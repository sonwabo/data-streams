package uk.co.omniolytics.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.omniolytics.model.DeviceDataMessage;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDataResponse {
    private Collection<DeviceDataMessage> data;
    private String bookmark;

    public boolean isAtEnd() {
        return bookmark == null || bookmark.length() == 0 || "nil".equals(bookmark) || "null".equals(bookmark);
    }
}
