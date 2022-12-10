import { CoffeeRoast } from "./coffee-roast";

export interface CoffeeBeanDashboardDto {
    id: Number,
    name: String,
    coffeeRoast: CoffeeRoast,
    description: String
}