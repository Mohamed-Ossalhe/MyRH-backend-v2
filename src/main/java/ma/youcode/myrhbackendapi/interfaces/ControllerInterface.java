package ma.youcode.myrhbackendapi.interfaces;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 *
 * @param <R> Request DTO
 * @param <D> Response DTO
 * @param <ID> Entity Primary Key
 */
public interface ControllerInterface<R, D, ID> {
    /**
     *
     * @param id primary key
     * @return Response Entity with the found resource
     */
    ResponseEntity<D> get(@PathVariable ID id);

    /**
     *
     * @return Response Entity with List of all resources
     */
    ResponseEntity<List<D>> getAll();

    /**
     *
     * @param pageable {@link Pageable}
     * @return Response Entity with a list of paginated resources
     */
    ResponseEntity<Page<D>> getAllPaginated(Pageable pageable);

    /**
     *
     * @param r request dto
     * @return Response Entity of created resource
     */
    ResponseEntity<D> create(@Valid @RequestBody R r);

    /**
     *
     * @param r request dto includes information that will be replacing the old resource
     * @param id primary key of the specified resource will be updated
     * @return Response entity with the updated resource
     */
    ResponseEntity<D> update(@Valid @RequestBody R r, @PathVariable ID id);

    /**
     *
     * @param id resource primary key will be deleted
     * @return Response entity with a success or failure message
     */
    ResponseEntity<Map<String, String>> destroy(@PathVariable ID id);
}
