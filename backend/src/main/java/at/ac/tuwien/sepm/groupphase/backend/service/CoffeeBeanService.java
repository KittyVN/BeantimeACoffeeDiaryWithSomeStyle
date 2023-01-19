package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDashboardDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanExtractionsListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

public interface CoffeeBeanService {
    /**
     * Fetches all saved coffee beans belonging to one User from the persistent data storage.
     *
     * @param id The id of the requesting user
     * @return a stream of the found coffee beans
     * @throws NotFoundException if id doesnt exist
     */
    Stream<CoffeeBeanDashboardDto> getAllByUser(Long id) throws NotFoundException;

    /**
     * Fetches all saved coffee beans from the persistent data storage
     * that match the criteria specified in @code(searchParams).
     *
     * @param searchParams A dto containing the search parameters
     * @param id the id of the user searching
     * @return a stream of the found coffee beans
     * @throws NotFoundException if id doesnt exist
     */
    Stream<CoffeeBeanDashboardDto> search(CoffeeBeanSearchDto searchParams, Long id) throws NotFoundException;

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
     * @throws NotFoundException coffee bean id doesnt exist
     * @throws ResponseStatusException if there was a conflict
     */
    CoffeeBeanDto update(CoffeeBeanDto coffeeBeanDto) throws NotFoundException, ResponseStatusException;

    /**
     * Deletes an already persisted coffee bean by its id.
     *
     * @param id of the coffee bean to be deleted
     */
    void delete(Long id);

    /**
     * Gets an already persisted coffee bean by its id.
     *
     * @param id the id of the coffee bean to get
     * @return the coffee bean that has been found
     * @throws NotFoundException in case no coffee bean is found with the given id
     */
    CoffeeBeanDto getById(Long id) throws NotFoundException;

    /**
     * Get the top 5 most extracted coffee beans by user id.
     *
     * @param id of the user.
     * @return a List of CoffeeBeanExtractionsListDto
     */
    List<CoffeeBeanExtractionsListDto> getTop5ExtractedByUserId(Long id);

    /**
     * Get the top 5 on average best rated coffees of a specific user.
     *
     * @param id of the user
     * @return a List of CoffeeBeanRatingListDto
     */
    List<CoffeeBeanRatingListDto> getTop5RatedByUserId(Long id);
}
