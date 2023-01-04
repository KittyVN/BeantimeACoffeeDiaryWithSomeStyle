package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
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
        Recipe recipe = new Recipe(recipeDto.getDescription());
        if (extraction.get().getRecipe() == null) {
            RecipeDto recipeDtoToReturn = mapper.entityToDto(recipeRepository.save(recipe));
            recipeDtoToReturn.setExtractionId(extraction.get().getId());
            extractionRepository.addRecipeToExtraction(extraction.get().getId(), recipeDtoToReturn.getId());
            return recipeDtoToReturn;
        } else {
            throw new FileAlreadyExistsException(String.format("recipe for this extraction with ID %d already exists", extraction.get().getId()));
        }
    }

    @Override
    public Stream<RecipeDto> getAll() {
        LOGGER.trace("getAll()");
        return recipeRepository.findAll().stream().map(recipe -> mapper.entityToDto(recipe));
    }
}
