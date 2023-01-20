export interface CommunityRecipeDto {
  recipeId: number;
  recipeShared: boolean;
  extractionId: number;
  extractionDate: Date;
  extractionBrewMethod: string;
  extractionGrindSetting: string;
  extractionWaterTemperature: number;
  extractionDose: number;
  extractionWaterAmount: number;
  extractionBrewTime: number;
  extractionBody: number;
  extractionAcidity: number;
  extractionAromatics: number;
  extractionSweetness: number;
  extractionAftertaste: number;
  extractionRatingNotes: string;
  extractionRecipeSteps: string;
  coffeeBeanId: number;
  coffeeBeanName: string;
  coffeeBeanPrice: number;
  coffeeBeanBlend: string;
  coffeeBeanOrigin: string;
  coffeeBeanHeight: number;
  coffeeBeanRoast: string;
  coffeeBeanUrl: string;
  coffeeBeanDescription: string;
  coffeeBeanStrength: string;
}
