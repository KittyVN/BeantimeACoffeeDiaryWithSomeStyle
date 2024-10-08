package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeSearchCommunityDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import at.ac.tuwien.sepm.groupphase.backend.exception.AuthorizationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.mapper.RecipeMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.ExtractionRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.RecipeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.nio.file.FileAlreadyExistsException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RecipeRepository recipeRepository;
    private final ExtractionRepository extractionRepository;
    private final UserRepository userRepository;
    private final RecipeMapper mapper;


    public RecipeServiceImpl(RecipeRepository recipeRepository, ExtractionRepository extractionRepository, UserRepository userRepository, RecipeMapper mapper) {
        this.recipeRepository = recipeRepository;
        this.extractionRepository = extractionRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public RecipeListDto create(RecipeListDto recipeListDto) throws FileAlreadyExistsException {
        LOGGER.trace("create {}", recipeListDto);
        Optional<Extraction> extraction = extractionRepository.findById(recipeListDto.getExtractionId());
        Object currentUserId = getCurrentAuthenticatedUserId();
        if (extraction.isPresent()) {
            if (!extraction.get().getCoffeeBean().getUser().getId().toString().equals(currentUserId)) {
                throw new AuthorizationException("You cannot make a recipe with a extraction that you didn't make!");
            }
        }
        Recipe recipeExist = recipeRepository.findRecipeByExtraction_Id(recipeListDto.getExtractionId());
        Recipe recipe = new Recipe(false, extraction.get());
        if (recipeExist == null) {
            return mapper.entityToListDto(recipeRepository.save(recipe));
        } else {
            throw new FileAlreadyExistsException(String.format("recipe for this extraction with ID %d already exists", recipeExist.getExtraction().getId()));
        }
    }

    @Override
    public RecipeListDto update(RecipeListDto recipeListDto) throws NotFoundException {
        LOGGER.trace("update {}", recipeListDto);
        Optional<Extraction> extraction = extractionRepository.findById(recipeListDto.getExtractionId());
        Object currentUserId = getCurrentAuthenticatedUserId();
        if (extraction.isPresent()) {
            if (!extraction.get().getCoffeeBean().getUser().getId().toString().equals(currentUserId)) {
                throw new AuthorizationException("You cannot share something that's not yours!");
            }
        }
        Recipe recipe = recipeRepository.findRecipeByExtraction_Id(recipeListDto.getExtractionId());
        if (recipe == null) {
            throw new NotFoundException(String.format("No recipe with extraction ID %d found", recipeListDto.getExtractionId()));
        } else {
            recipe.setShared(recipeListDto.isShared());
            return mapper.entityToListDto(recipeRepository.save(recipe));
        }
    }

    @Override
    public Stream<RecipeListDto> getAll() {
        LOGGER.trace("getAll()");
        return recipeRepository.findRecipesBySharedIsTrue()
            .stream().map(mapper::entityToListDto);
    }

    @Override
    public Stream<RecipeDetailDto> getAllWithExtractions() {
        LOGGER.trace("getAllWithExtractions()");
        return recipeRepository.findRecipesBySharedIsTrue().stream().map(mapper::entityToDetailDto);
    }

    @Override
    public Stream<RecipeDetailDto> searchCommunityRecipes(RecipeSearchCommunityDto searchParams) {
        LOGGER.trace("searchCommunityRecipes()");
        Stream<Object> recipes = recipeRepository.searchAllRecipesJoinedWithExtraction(
            searchParams.getName(),
            searchParams.getBrewMethod(),
            searchParams.getRoast(),
            searchParams.getBlend(),
            searchParams.getRoast() == null ? "" : searchParams.getRoast().toString(),
            searchParams.getBrewMethod() == null ? "" : searchParams.getBrewMethod().toString())
            .stream();
        return recipes.map(recipe -> mapper.objectToDto(recipe));
    }

    @Override
    public RecipeListDto getByExtractionId(Long id) throws NotFoundException {
        Recipe recipe = recipeRepository.findRecipeByExtraction_Id(id);
        if (recipe == null) {
            throw new NotFoundException(String.format("No recipe for the extraction with the ID %d found", id));
        }
        return mapper.entityToListDto(recipe);
    }

    @Override
    public RecipeDetailDto getById(Long id) throws NotFoundException {
        Recipe recipe = recipeRepository.findRecipeById(id);
        if (recipe == null) {
            throw new NotFoundException(String.format("No recipe with the ID %d found", id));
        }
        return mapper.entityToDetailDto(recipe);
    }

    @Override
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Stream<RecipeDetailDto> getAllByUserId(Long id) throws NotFoundException {
        LOGGER.trace("getAllByUserId({})", id);
        if (userRepository.existsById(id)) {
            return recipeRepository.findRecipesByExtraction_CoffeeBean_User_Id(id).stream().map(mapper::entityToDetailDto);
        } else {
            throw new NotFoundException(String.format("No user with ID %d found", id));
        }
    }

    private Object getCurrentAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        return principal;
    }
}
