package com.abhi.dto.mappers;

import com.abhi.dto.request.MenuItemRequest;
import com.abhi.dto.response.MenuItemResponse;
import com.abhi.model.MenuItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {


    @Mapping(target = "itemId", ignore = true)
    MenuItem toMenuItemEntity(MenuItemRequest menuItemRequest);

    MenuItemResponse toMenuItemResponse(MenuItem menuItemEntity);

    @Mapping(target = "itemId",ignore = true)
    void updateMenuItemEntityFromDto(MenuItemRequest menuItemRequest,@MappingTarget MenuItem menuItemEntity);

    @Mapping(target = "itemId",ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialEntityUpdateFromDto(MenuItemRequest menuItemRequest, @MappingTarget MenuItem menuItem);

}
