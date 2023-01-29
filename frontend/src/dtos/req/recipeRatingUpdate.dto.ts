export interface RecipeRatingUpdateDto {
  id: number;
  recipeId: number;
  authorId: number;
  rating: number;
  text?: string;
}
