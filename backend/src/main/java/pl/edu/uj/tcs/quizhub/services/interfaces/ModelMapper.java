package pl.edu.uj.tcs.quizhub.services.interfaces;

public interface ModelMapper<R, S> {
    R from(S obj);

    S onto(R obj);
}
