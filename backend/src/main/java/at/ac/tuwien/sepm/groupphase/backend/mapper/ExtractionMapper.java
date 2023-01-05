package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionCreateDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.CoffeeBean;
import at.ac.tuwien.sepm.groupphase.backend.entity.Extraction;
import org.springframework.stereotype.Component;

@Component
public class ExtractionMapper {

    /**
     * Converts a Extraction Entity object to a {@link at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDetailDto}.
     *
     * @param extraction the object to convert
     * @return the converted Extraction {@link ExtractionDetailDto}
     */
    public ExtractionDetailDto entityToDto(Extraction extraction) {
        Long id;
        if (extraction.getCoffeeBean() == null) {
            id = null;
        } else {
            id = extraction.getCoffeeBean().getId();
        }
        int overallRating;
        if (extraction.getAcidity() != null && extraction.getAftertaste() != null && extraction.getBody() != null && extraction.getSweetness() != null &&
            extraction.getAromatics() != null) {
            overallRating =
                extraction.getAftertaste() + extraction.getAcidity() + extraction.getBody() + extraction.getSweetness() + extraction.getAromatics();
        } else {
            overallRating = 0;
        }
        return new ExtractionDetailDto(
            extraction.getId(),
            extraction.getExtractionDate(),
            extraction.getBrewMethod(),
            extraction.getGrindSetting(),
            extraction.getWaterTemperature(),
            extraction.getDose(),
            extraction.getWaterAmount(),
            extraction.getBrewTime(),
            extraction.getBody(),
            extraction.getAcidity(),
            extraction.getAromatics(),
            extraction.getSweetness(),
            extraction.getAftertaste(),
            extraction.getRatingNotes(),
            overallRating,
            id
        );

    }

    public ExtractionCreateDto entityToCreateDto(Extraction extraction) {
        Long id;
        if (extraction.getCoffeeBean() == null) {
            id = null;
        } else {
            id = extraction.getCoffeeBean().getId();
        }

        return new ExtractionCreateDto(
            extraction.getId(),
            extraction.getBrewMethod(),
            extraction.getGrindSetting(),
            extraction.getWaterTemperature(),
            extraction.getDose(),
            extraction.getWaterAmount(),
            extraction.getBrewTime(),
            extraction.getBody(),
            extraction.getAcidity(),
            extraction.getAromatics(),
            extraction.getSweetness(),
            extraction.getAftertaste(),
            extraction.getRatingNotes(),
            id
        );
    }
}
