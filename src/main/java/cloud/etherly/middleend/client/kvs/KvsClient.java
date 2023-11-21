package cloud.etherly.middleend.client.kvs;

import java.util.List;
import java.util.Optional;

public interface KvsClient<T> {
  Optional<T> find(String key);

  List<T> findAll();

  T save(String key, T value);

  void delete(String key);

}
