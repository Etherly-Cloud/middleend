package cloud.etherly.middleend.business.engine.task.cache;

import java.util.Map;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class TaskCacheItem {
  private Map.Entry<String, Object> entry;
  private TaskCacheItemStatus status = TaskCacheItemStatus.PENDING;

  @SneakyThrows
  public synchronized Map.Entry<String, Object> getEntry() {
    if (status == TaskCacheItemStatus.PENDING) {
      this.wait();
    }
    return entry;
  }

  public synchronized void setEntry(final Map.Entry<String, Object> entry) {
    this.entry = entry;
    this.status = TaskCacheItemStatus.DONE;
    this.notifyAll();
  }

}
