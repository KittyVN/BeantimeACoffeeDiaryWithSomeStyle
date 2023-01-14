package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanExtractionsListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.util.List;
import java.util.stream.Stream;

public interface CoffeeBeanService {
    /**
     * Fetches all saved coffee beans belonging to one User from the persistent data storage.
     *
     * @param id The id of the requesting user
     * @return a stream of the found coffee beans
     */
    Stream<CoffeeBeanDashboardDto> getAllByUser(Long id);

    /**
     * Fetches all saved coffee beans from the persistent data storage
     * that match the criteria specified in @code(searchParams).
     *
     * @param searchParams A dto containing the search parameters
     * @param id the id of the user searching
     * @return a stream of the found coffee beans
     */
    Stream<CoffeeBeanDashboardDto> search(CoffeeBeanSearchDto searchParams, Long id);

    /**
     * Adds a new CoffeeBean to the persistent data storage.
     *
     * @param coffeeBeanDto to add
     * @return the created coffee bean
     */
    CoffeeBeanDto create(CoffeeBeanDto coffeeBeanDto);

    /**
     * Updates a CoffeeBean that is already persistent in data storage.
     *
     * @param coffeeBeanDto to update
     * @return the updated CoffeeBean
     */
    CoffeeBeanDto update(CoffeeBeanDto coffeeBeanDto);

    /**
     * Deletes an already persisted coffee bean by its id.
     *
     * @param id of the coffee bean to be deleted
     * @throws NotFoundException in case the coffee bean with given id is not found
     */
    void delete(Long id) throws NotFoundException;

    /**
     * Gets an already persisted coffee bean by its id.
     *
     * @param id the id of the coffee bean to get
     * @return the coffee bean that has been found
     * @throws NotFoundException in case no coffee bean is found with the given id
     */
    CoffeeBeanDto getById(Long id) throws NotFoundException;

    /**
     * Get the top 10 most extracted coffee beans by user id.
     *
     * @param id of the user.
     * @return a List of CoffeeBeanExtractionsListDto
     */
    List<CoffeeBeanExtractionsListDto> getTop10ExtractedByUserId(Long id);
}
