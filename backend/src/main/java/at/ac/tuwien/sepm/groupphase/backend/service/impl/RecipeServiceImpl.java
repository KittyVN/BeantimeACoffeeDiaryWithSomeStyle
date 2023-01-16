package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CommunityRecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.mapper.RecipeMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.ExtractionRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.RecipeRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final RecipeMapper mapper;


    public RecipeServiceImpl(RecipeRepository recipeRepository, ExtractionRepository extractionRepository, RecipeMapper mapper) {
        this.recipeRepository = recipeRepository;
        this.extractionRepository = extractionRepository;
        this.mapper = mapper;
    }

    @Override
    public RecipeDto create(RecipeDto recipeDto) throws FileAlreadyExistsException {
        LOGGER.trace("create {}", recipeDto);
        Optional<Extraction> extraction = extractionRepository.findById(recipeDto.getExtractionId());
        Recipe recipeExist = recipeRepository.findRecipeByExtractionId(recipeDto.getExtractionId());
        Recipe recipe = new Recipe(recipeDto.getDescription(), extraction.get());
        if (recipeExist == null) {
            return mapper.entityToDto(recipeRepository.save(recipe));
        } else {
            throw new FileAlreadyExistsException(String.format("recipe for this extraction with ID %d already exists", recipeExist.getExtraction().getId()));
        }
    }

    @Override
    public RecipeDto update(RecipeDto recipeDto) throws NotFoundException {
        LOGGER.trace("update {}", recipeDto);
        Recipe recipe = recipeRepository.findRecipeByExtractionId(recipeDto.getExtractionId());
        if (recipe == null) {
            throw new NotFoundException(String.format("No recipe with extraction ID %d found", recipeDto.getExtractionId()));
        } else {
            Recipe newRecipe = recipe;
            newRecipe.setDescription(recipeDto.getDescription());
            return mapper.entityToDto(recipeRepository.save(newRecipe));
        }
    }

    @Override
    public Stream<RecipeDto> getAll() {
        LOGGER.trace("getAll()");
        return recipeRepository.findAllRecipes().stream().map(recipe -> mapper.entityToDto(recipe));
    }

    @Override
    public Stream<CommunityRecipeDto> getAllWithExtractions() {
        LOGGER.trace("getAllWithExtractions()");
        Stream<Object> recipes = recipeRepository.findAllRecipesJoinedWithExtraction().stream();
        Stream<CommunityRecipeDto> recipesDto = recipes.map(recipe -> mapper.objectToDto(recipe));
        return recipesDto;
    }

    @Override
    public RecipeDto getByExtractionId(Long id) throws NotFoundException {
        Recipe recipe = recipeRepository.findRecipeByExtractionId(id);
        if (recipe == null) {
            throw new NotFoundException(String.format("No recipe for the extraction with the ID %d found", id));
        }
        return mapper.entityToDto(recipe);
    }

    @Override
    public CommunityRecipeDto getById(Long id) throws NotFoundException {
        Object recipe = recipeRepository.findRecipeJoinedWithExtractionById(id);
        if (recipe == null) {
            throw new NotFoundException();
        }
        return mapper.objectToDto(recipe);
    }

    @Override
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Stream<CommunityRecipeDto> getAllByUserId(Long id) {
        LOGGER.trace("getAllByUserId({})", id);
        Stream<Object> recipes = recipeRepository.findAllRecipesByUserId(id).stream();
        return recipes.map(recipe -> mapper.objectToDto(recipe));
    }
}
