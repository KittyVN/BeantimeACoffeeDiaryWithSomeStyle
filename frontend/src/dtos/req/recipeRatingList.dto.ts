export interface RecipeRatingListDto {
  id: number;
  recipeId: number;
  authorId: number;
  rating: number;
  text?: string;
}
