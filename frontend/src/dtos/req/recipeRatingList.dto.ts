export interface RecipeRatingListDto {
  id: number;
  recipeId: number;
  authorId: number;
  authorUsername: string;
  timestamp: Date;
  rating: number;
  text?: string;
}
