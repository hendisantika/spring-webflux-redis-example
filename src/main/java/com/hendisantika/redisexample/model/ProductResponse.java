package com.hendisantika.redisexample.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneOffset;

/**
 * Created by IntelliJ IDEA.
 * Project : redis-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/29/23
 * Time: 09:37
 * To change this template use File | Settings | File Templates.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "description",
        "active",
        "startDate",
        "endDate"
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String id;
    private String name;
    private String description;
    private boolean active;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;

    /**
     * Creates an instance of {@link ProductResponse} from {@link Product}.
     *
     * @param product product to convert
     * @return a {@link ProductResponse}
     */
    public static ProductResponse from(final Product product) {
        final ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setActive(product.isActive());
        response.setStartDate(Instant.ofEpochSecond(product.getStartTime()).atOffset(ZoneOffset.UTC).toString());
        response.setEndDate(Instant.ofEpochSecond(product.getEndTime()).atOffset(ZoneOffset.UTC).toString());

        return response;
    }

}
