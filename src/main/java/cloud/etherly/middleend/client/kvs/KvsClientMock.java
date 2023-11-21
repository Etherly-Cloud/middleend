package cloud.etherly.middleend.client.kvs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KvsClientMock<T> implements KvsClient<T> {

  private final Map<String, T> repository = new HashMap<>();

  private final String prefix;

  @Override
  public Optional<T> find(final String key) {
    return Optional.ofNullable(repository.get(prefix + key));
  }

  @Override
  public List<T> findAll() {
    return repository.values().stream().toList();
  }

  @Override
  public T save(final String key, final T value) {
    return repository.put(getKey(key), value);
  }

  @Override
  public void delete(final String key) {
    repository.remove(getKey(key));
  }

  private String getKey(final String key) {
    return prefix + key;
  }

}
