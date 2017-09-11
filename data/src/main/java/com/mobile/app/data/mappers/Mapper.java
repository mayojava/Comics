package com.mobile.app.data.mappers;

import com.app.marvel.comics.domain.entity.Comic;
import com.mobile.app.data.db.entities.ComicsEntity;
import com.mobile.app.data.models.CreatorList;
import com.mobile.app.data.models.CreatorSummary;
import com.mobile.app.data.models.Price;
import com.mobile.app.data.models.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Mapper {
    @Inject public Mapper() {}

    public List<Comic> mapToDomainComicModel(final List<ComicsEntity> entities) {
        final List<Comic> result = new ArrayList<>();

        for (ComicsEntity entity: entities) {
            final Comic comic = Comic.builder()
                    .title(entity.getTitle())
                    .thumbnail(entity.getThumbnail())
                    .description(entity.getDescription())
                    .pageCount(entity.getPageCount())
                    .price(entity.getPrice())
                    .priceType(entity.getPriceType())
                    .authors(entity.getAuthors())
                    .build();

            result.add(comic);
        }

        return result;
    }

    public ComicsEntity mapToDbEntity(final Result result) {
        String authors;
        final CreatorList creatorList = result.getCreator();
        if (creatorList.getAvailable() == 0) {
            authors = "";
        } else {
            final StringBuilder stringBuilder = new StringBuilder();
            final List<CreatorSummary> creators = creatorList.getItems();

            for (int i = 0; i < creators.size(); i++) {
                stringBuilder.append(creators.get(i).getName());

                if (i != creators.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            authors = stringBuilder.toString();
        }

        float price = 0.0f;
        String priceType = "";

        if (result.getPrices().size() != 0) {
            final Price p = result.getPrices().get(0);
            price = p.getPrice();
            priceType = p.getType();
        }

        String thumnbnail = "";
        if (result.getThumbnail() != null) {
            thumnbnail = result.getThumbnail().getPath() + "." + result.getThumbnail().getExtension();
        }

        return new ComicsEntity(
                result.getId(),
                thumnbnail,
                result.getTitle() == null ? "" : result.getTitle(),
                result.getDescription() == null ? "" : result.getDescription(),
                result.getPageCount(),
                price,
                priceType,
                authors);
    }
}
