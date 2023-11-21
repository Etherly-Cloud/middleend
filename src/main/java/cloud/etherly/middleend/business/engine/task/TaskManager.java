package cloud.etherly.middleend.business.engine.task;

import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.Marshaller;
import cloud.etherly.middleend.business.engine.task.cache.TaskCacheItem;
import cloud.etherly.middleend.business.engine.task.cache.TaskContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class TaskManager {
  private final ObjectMapper objectMapper = new ObjectMapper();

  public static String getTaskField(final String name) {
    final String finalName = name.replace("Task", "");
    return finalName.substring(0, 1).toLowerCase() + finalName.substring(1);
  }

  public <T> Optional<T> tryExecuteTask(final Context context, final Task<T> task) {
    try {
      return Optional.ofNullable(task.execute(context));
    } catch (final Exception e) {
      return Optional.empty();
    }
  }

  public <I, O> O executeTask(final Context context, final Task<I> task, final Marshaller<I, O> marshaller) {
    return marshaller.marshall(executeTask(context, task).getValue());
  }

  public <T> Map.Entry<String, T> executeTask(final Context context, final Task<T> task) {
    final String taskField = getTaskField(task.getClass().getSimpleName());
    final TaskContext taskContext = context.getTaskContext();
    final Map<String, TaskCacheItem> taskCache = taskContext.getTaskParams();

    if (taskCache.containsKey(taskField)) {
      return (Map.Entry<String, T>) taskCache.get(taskField).getEntry();
    }

    final TaskCacheItem taskCacheItem = new TaskCacheItem();
    taskCache.put(taskField, taskCacheItem);

    final T value = task.execute(context);
    final Map.Entry<String, Object> result = Map.entry(taskField, value);
    taskCacheItem.setEntry(result);

    return (Map.Entry<String, T>) result;
  }

  public <T> T executeTasks(final Context context, final Class<T> clazz, final List<Task<?>> tasks, final boolean sync) {
    return objectMapper.convertValue(executeTasks(context, tasks, sync), clazz);
  }

  public Map<String, Object> executeTasks(final Context context, final List<Task<?>> tasks, final boolean sync) {
    final Stream<Task<?>> taskStream = sync ? tasks.stream() : tasks.parallelStream();

    return taskStream.map(task -> executeTask(context, task)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public <I, O> I executeTasks(final Context context, final Marshaller<O, I> marshaller, final List<Task<?>> tasks) {
    return marshaller.marshall(executeTasks(context, marshaller.getInputClass(), tasks, false));
  }

}
