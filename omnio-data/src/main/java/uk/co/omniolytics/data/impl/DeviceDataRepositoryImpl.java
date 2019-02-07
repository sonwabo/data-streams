package uk.co.omniolytics.data.impl;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.JsonIndex;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.query.Selector;
import lombok.extern.slf4j.XSlf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import uk.co.omniolytics.data.DeviceDataRepository;
import uk.co.omniolytics.data.DeviceDataResponse;
import uk.co.omniolytics.model.DeviceDataMessage;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.cloudant.client.api.query.Expression.eq;
import static com.cloudant.client.api.query.Expression.gt;
import static com.cloudant.client.api.query.Expression.in;
import static com.cloudant.client.api.query.Expression.lt;
import static com.cloudant.client.api.query.Operation.and;

@Component
@XSlf4j
public class DeviceDataRepositoryImpl implements DeviceDataRepository {
    public static final String ASCENDING_DEV = "ascending_dev";
    public static final String DESCENDING_DEV = "descending_dev";
    public static final String ASCENDING_DEVID = "deviceId";
    private Database database;

    public DeviceDataRepositoryImpl(Database database) {
        this.database = database;
    }

    @PostConstruct
    public void indexCreator() {
        final String ascendingIndexDefinition = JsonIndex.builder().designDocument(ASCENDING_DEV).asc("timestamp", "deviceId").definition();
        log.info("creating index:{}", ascendingIndexDefinition);
        database.createIndex(ascendingIndexDefinition);

        final String descendingIndexDefinition = JsonIndex.builder().designDocument(DESCENDING_DEV).desc("timestamp", "deviceId").definition();
        log.info("creating index:{}", descendingIndexDefinition);
        database.createIndex(descendingIndexDefinition);

        final String deviceIdIndexDefinition = JsonIndex.builder().designDocument(ASCENDING_DEVID).asc("deviceId").definition();
        log.info("creating index:{}", deviceIdIndexDefinition);
        database.createIndex(deviceIdIndexDefinition);

    }

    @Override
    public DeviceDataMessage save(DeviceDataMessage data) {
        log.entry(data);
        Response response = database.save(data);
        if (response.getError() != null) {
            log.error("query:response:{}", response.getError(), response.getReason());
        }
        if (data.get_id() == null) {
            data.set_id(response.getId());
        }
        data.set_rev(response.getRev());
        return log.exit(data);
    }

    private QueryResult<DeviceDataMessage> _query(String bookmark, String deviceId, DateTime from, DateTime to, long limit, boolean descending) {
        log.entry(deviceId, from, to, limit);
        Assert.notNull(deviceId, "deviceId required");
        Assert.hasText(deviceId, "deviceId required");
        List<Selector> selectors = new ArrayList<>();
        selectors.add(eq("deviceId", deviceId));
        String query = createQuery(bookmark, from, to, limit, descending, selectors);
        return executeQuery(query);
    }

    private String createQuery(String bookmark, DateTime from, DateTime to, long limit, boolean descending, List<Selector> selectors) {
        DateTime min = from != null && to != null ? from.isBefore(to) ? from : to : from;
        DateTime max = from != null && to != null ? to.isAfter(from) ? to : from : to;
        log.debug("createQuery:min={},max={}", min, max);
        if (min != null) {
            selectors.add(gt("timestamp", min.toDateTime(DateTimeZone.UTC).toString()));
        }
        if (max != null) {
            selectors.add(lt("timestamp", max.toDateTime(DateTimeZone.UTC).toString()));
        }
        QueryBuilder builder = new QueryBuilder(and(selectors.toArray(new Selector[selectors.size()])));
        if (limit > 0) {
            builder.limit(limit);
        }
        if (bookmark != null && StringUtils.hasText(bookmark) && !bookmark.equals("nil")) {
            builder.bookmark(bookmark);
        }
        String index = null;
        if (from == null && to == null) {
            index = ASCENDING_DEVID;
        } else {
            if (descending) {
                index = DESCENDING_DEV;
            } else {
                index = ASCENDING_DEV;
            }
        }
        if (index != null) {
            builder.useIndex(index);
        }
        final String queryString = builder.build();
        log.info("query:{}", queryString);
        return queryString;
    }

    @Override
    public DeviceDataResponse query(Set<String> deviceId, DateTime from, DateTime to, long limit, boolean descending, String bookmark) {
        QueryResult<DeviceDataMessage> result = _query(bookmark, deviceId, from, to, limit, descending);
        return new DeviceDataResponse(result.getDocs(), result.getBookmark());
    }

    private QueryResult<DeviceDataMessage> _query(String bookmark, Set<String> deviceIds, DateTime from, DateTime to, long limit, boolean descending) {
        log.entry(deviceIds, from, to, limit);
        Assert.notNull(deviceIds, "deviceIds required");
        Assert.notEmpty(deviceIds, "deviceIds required");
        List<Selector> selectors = new ArrayList<>();
        selectors.add(in("deviceId", deviceIds.toArray()));
        String query = createQuery(bookmark, from, to, limit, descending, selectors);
        return executeQuery(query);
    }

    private QueryResult<DeviceDataMessage> executeQuery(String query) {
        log.debug("query:{}", query);
        QueryResult<DeviceDataMessage> result = database.query(query, DeviceDataMessage.class);
        if (result.getWarning() != null) {
            log.warn("query:response:{}", result.getWarning());
        }
        return result;
    }

    @Override
    public List<DeviceDataMessage> query(String deviceId, DateTime from, DateTime to) {
        return query(deviceId, from, to, -1L, false);
    }

    @Override
    public List<DeviceDataMessage> query(String deviceId, DateTime from, DateTime to, long limit, boolean descending) {
        List<DeviceDataMessage> results = new ArrayList<>();
        String bookmark = null;
        String prevBookmark = null;
        do {
            QueryResult<DeviceDataMessage> result = _query(bookmark, deviceId, from, to, limit, descending);
            results.addAll(result.getDocs());
            log.debug("entries:{}", result.getDocs().size());
            if (result.getDocs().isEmpty()) {
                break;
            }
            prevBookmark = bookmark;
            bookmark = result.getBookmark();
            if (bookmark != null && (bookmark.equals("") || bookmark.equals("nil"))) {
                bookmark = null;
            }
        } while (bookmark != null && !bookmark.equals(prevBookmark));
        return results;
    }

    @Override
    public List<DeviceDataMessage> queryRecent(String deviceId, long limit) {
        return query(deviceId, null, null, 5, true);
    }

    @Override
    public List<DeviceDataMessage> query(Set<String> deviceIds, DateTime from, DateTime to, long limit, boolean descending) {
        List<DeviceDataMessage> results = new ArrayList<>();
        String bookmark = null;
        String prevBookmark = null;
        do {
            QueryResult<DeviceDataMessage> result = _query(bookmark, deviceIds, from, to, limit, descending);
            results.addAll(result.getDocs());
            log.debug("entries:{}", result.getDocs().size());
            if (result.getDocs().isEmpty()) {
                break;
            }
            prevBookmark = bookmark;
            bookmark = result.getBookmark();
            if (bookmark != null && (bookmark.equals("") || bookmark.equals("nil"))) {
                bookmark = null;
            }
        } while (bookmark != null && !bookmark.equals(prevBookmark));
        return results;
    }
}
