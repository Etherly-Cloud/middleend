package cloud.etherly.middleend.business.engine.task;

import cloud.etherly.middleend.business.engine.Context;

@FunctionalInterface
public interface Task<T> {

  T execute(Context context);

}
