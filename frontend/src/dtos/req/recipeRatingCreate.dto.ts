export interface RecipeRatingCreateDto {
  recipeId: number;
  authorId: number;
  rating: number;
  text?: string;
}
