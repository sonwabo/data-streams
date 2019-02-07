package uk.co.omniolytics.data;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import uk.co.omniolytics.model.DeviceDataMessage;

import java.util.List;
import java.util.Set;

@Repository
public interface DeviceDataRepository {
    DeviceDataMessage save(DeviceDataMessage data);

    DeviceDataResponse query(Set<String> deviceId, DateTime from, DateTime to, long limit, boolean descending, String bookmark);

    List<DeviceDataMessage> query(Set<String> deviceId, DateTime from, DateTime to, long limit, boolean descending);

    List<DeviceDataMessage> query(String deviceId, DateTime from, DateTime to, long limit, boolean descending);

    List<DeviceDataMessage> query(String deviceId, DateTime from, DateTime to);

    List<DeviceDataMessage> queryRecent(String deviceId, long limit);

}
