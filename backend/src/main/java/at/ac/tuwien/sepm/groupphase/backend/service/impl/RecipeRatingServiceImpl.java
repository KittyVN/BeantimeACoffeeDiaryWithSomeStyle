package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.RecipeRatingUpdateDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Recipe;
import at.ac.tuwien.sepm.groupphase.backend.entity.RecipeRating;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.mapper.RecipeRatingMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.RecipeRatingRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.RecipeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.RecipeRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@Service
public class RecipeRatingServiceImpl implements RecipeRatingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RecipeRatingRepository recipeRatingRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeRatingMapper recipeRatingMapper;

    public RecipeRatingServiceImpl(RecipeRatingRepository recipeRatingRepository, RecipeRatingMapper recipeRatingMapper,
                                   RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRatingRepository = recipeRatingRepository;
        this.recipeRatingMapper = recipeRatingMapper;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RecipeRatingListDto create(RecipeRatingCreateDto recipeRatingCreateDto) {
        LOGGER.trace("create {}", recipeRatingCreateDto);
        User author = userRepository.findFirstById(recipeRatingCreateDto.getAuthorId());
        Recipe recipe = recipeRepository.findRecipeById(recipeRatingCreateDto.getRecipeId());

        if (author.getId() == recipe.getExtraction().getCoffeeBean().getUser().getId()) {
            throw new ConflictException("Recipe owners cannot create ratings for their own recipe");
        }

        RecipeRating rating = new RecipeRating(recipe, author,
            recipeRatingCreateDto.getRating(), recipeRatingCreateDto.getText());
        recipeRatingRepository.save(rating);
        return recipeRatingMapper.entityToDto(rating);
    }

    @Override
    public Stream<RecipeRatingListDto> getByRecipeId(long recipeId) {
        LOGGER.trace("getByRecipeId {}", recipeId);
        return recipeRatingRepository.findRecipeRatingByRecipe_Id(recipeId)
            .stream().map(recipeRatingMapper::entityToDto);
    }

    @Override
    public RecipeRatingListDto update(RecipeRatingUpdateDto recipeRatingUpdateDto) {
        LOGGER.trace("update {}", recipeRatingUpdateDto);
        RecipeRating rating = recipeRatingRepository.findRecipeRatingById(recipeRatingUpdateDto.getId());
        rating.setRating(recipeRatingUpdateDto.getRating());
        rating.setText(recipeRatingUpdateDto.getText());
        recipeRatingRepository.save(rating);
        return recipeRatingMapper.entityToDto(rating);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        LOGGER.trace("delete {}", id);
        recipeRatingRepository.deleteById(id);
    }
}
