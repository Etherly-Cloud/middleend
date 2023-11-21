package cloud.etherly.middleend.business.engine;

public interface Marshaller<I, O> {

  O marshall(I input);

  Class<I> getInputClass();

}
