package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanExtractionsListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionDayStatsDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionListDto;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.sql.Date;

@Component
public class UserProfileMapper {
    public ExtractionDayStatsDto tupleToExtractionDayStatsDto(Tuple tuple) {
        return new ExtractionDayStatsDto(
            tuple.get(0, Date.class).toLocalDate(),
            tuple.get(1, BigInteger.class).intValue(),
            tuple.get(2, Integer.class)
        );
    }

    public ExtractionListDto tupleToExtractionListDto(Tuple tuple) {
        return new ExtractionListDto(
            tuple.get(0, BigInteger.class).longValue(),
            tuple.get(1, java.sql.Timestamp.class).toLocalDateTime(),
            tuple.get(2, String.class),
            tuple.get(3, BigInteger.class).longValue(),
            tuple.get(4, Integer.class)
        );
    }

    public CoffeeBeanExtractionsListDto tupleToCoffeeBeanExtractionsListDto(Tuple tuple) {
        return new CoffeeBeanExtractionsListDto(
            tuple.get(0, BigInteger.class).longValue(),
            tuple.get(1, String.class),
            tuple.get(2, BigInteger.class).intValue()
        );
    }

    public CoffeeBeanRatingListDto tupleToCoffeeBeanRatingListDto(Tuple tuple) {
        return new CoffeeBeanRatingListDto(
            tuple.get(0, BigInteger.class).longValue(),
            tuple.get(1, String.class),
            tuple.get(2, Double.class)
        );
    }
}
