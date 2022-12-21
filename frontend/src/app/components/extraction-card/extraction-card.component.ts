import { Component, OnInit, Input } from '@angular/core';
import { BrewMethod } from 'src/dtos/req/brew-method.enum';
import { CoffeeGrindSetting } from 'src/dtos/req/coffee-grind-setting.enum';
import { ExtractionDetailDto } from 'src/dtos/req/extraction-detail.dto';

@Component({
  selector: 'app-extraction-card',
  templateUrl: './extraction-card.component.html',
  styleUrls: ['./extraction-card.component.css'],
})
export class ExtractionCardComponent {
  @Input() extraction?: ExtractionDetailDto;

  rated(): boolean {
    if (this.extraction) {
      return !(
        this.extraction.overallRating &&
        this.extraction.body &&
        this.extraction.acidity &&
        this.extraction.aromatics &&
        this.extraction.sweetness &&
        this.extraction.aftertaste
      );
    }
    return false;
  }

  realizeBody(): number {
    if (this.extraction?.body) {
      return this.extraction.body;
    }
    return -1;
  }

  realizeAcidity(): number {
    if (this.extraction?.acidity) {
      return this.extraction.acidity;
    }
    return -1;
  }

  realizeAromatics(): number {
    if (this.extraction?.aromatics) {
      return this.extraction.aromatics;
    }
    return -1;
  }

  realizeSweetness(): number {
    if (this.extraction?.sweetness) {
      return this.extraction.sweetness;
    }
    return -1;
  }

  realizeAftertaste(): number {
    if (this.extraction?.aftertaste) {
      return this.extraction.aftertaste;
    }
    return -1;
  }

  realizeOverallRating(): number {
    if (this.extraction?.overallRating) {
      return this.extraction.overallRating;
    }
    return -1;
  }

  ratingArray(): Array<number> {
    return [].constructor(5);
  }

  formatBrewTime(): string {
    if (this.extraction) {
      return (
        this.extraction.brewTime / (1000 * 60) +
        ':' +
        this.extraction.brewTime / 1000
      );
    }
    return '???';
  }

  formatBrewMethod(): string {
    if (this.extraction) {
      switch (this.extraction.brewMethod) {
        case BrewMethod.CREAM: {
          return 'Cream';
        }
        case BrewMethod.DRIP: {
          return 'Drip';
        }
        case BrewMethod.ESPRESSO: {
          return 'Espresso';
        }
        case BrewMethod.FOAM: {
          return 'Foam';
        }
        case BrewMethod.ICE: {
          return 'Ice';
        }
        case BrewMethod.MILK: {
          return 'Milk';
        }
        case BrewMethod.WATER: {
          return 'Water';
        }
      }
    }
    return '???';
  }

  formatGrindSetting(): string {
    if (this.extraction) {
      switch (this.extraction.grindSetting) {
        case CoffeeGrindSetting.COARSE: {
          return 'Coarse';
        }
        case CoffeeGrindSetting.MEDIUM_COARSE: {
          return 'Med-Coarse';
        }
        case CoffeeGrindSetting.MEDIUM: {
          return 'Medium';
        }
        case CoffeeGrindSetting.MEDIUM_FINE: {
          return 'Med-Fine';
        }
        case CoffeeGrindSetting.FINE: {
          return 'Fine';
        }
        case CoffeeGrindSetting.EXTRA_FINE: {
          return 'Extra-Fine';
        }
      }
    }
    return '???';
  }
}
