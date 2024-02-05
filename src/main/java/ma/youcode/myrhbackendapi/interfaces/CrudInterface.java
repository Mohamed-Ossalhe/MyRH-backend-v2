package ma.youcode.myrhbackendapi.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrudInterface<T, D, ID> {
    /**
     *
     * @return
     */
    List<T> getAll();

    /**
     *
     * @param pageable
     * @return
     */
    Page<T> getAll(Pageable pageable);

    /**
     *
     * @param id
     * @return
     */
    Optional<T> find(ID id);

    /**
     *
     * @param d
     * @return
     */
    Optional<T> create(D d);

    /**
     *
     * @param d
     * @return
     */
    Optional<T> update(D d, ID id);

    /**
     *
     * @param id
     * @return
     */
    boolean destroy(ID id);
}
