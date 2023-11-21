package cloud.etherly.middleend.business.engine.task.cache;

import cloud.etherly.middleend.business.engine.task.Task;
import cloud.etherly.middleend.business.engine.task.TaskManager;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

public class TaskContext {
  @Getter
  private final Map<String, TaskCacheItem> taskParams = new ConcurrentHashMap<>();

  public void addParam(final Task<?> task, final Object value) {
    final String key = TaskManager.getTaskField(task.getClass().getSimpleName());
    final TaskCacheItem taskCacheItem = new TaskCacheItem();
    taskCacheItem.setEntry(Map.entry(key, value));
    taskParams.put(key, taskCacheItem);
  }

  public <T> Optional<T> param(final String key) {
    final T value = (T) taskParams.get(key).getEntry().getValue();
    return Optional.ofNullable(value);
  }

}
