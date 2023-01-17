package io.okkio.mapper;

import io.okkio.domain.*;
import io.okkio.dto.BlogDto;
import io.okkio.dto.CareerDto;
import io.okkio.dto.CustomerSupportDto;
import io.okkio.dto.PartnerShipDto;
import io.okkio.repository.ImageRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class DiscoveryMapper {

    @Autowired
    private ImageRepository imageRepository;

    public abstract Careers toEntity(CareerDto dto, @MappingTarget Careers entity);

    public abstract CareerDto careerToDto(Careers dto);

    public abstract List<CareerDto> careersToDto(List<Careers> dto);

    public abstract Blogs toEntity(BlogDto dto, @MappingTarget Blogs entity);

    public abstract List<BlogDto> blogsToDto(List<Blogs> dto);

    @Mapping(target = "image", source = "imageId", qualifiedByName = "getImageById")
    @Mapping(target = "createdDate", source = "createdAt", qualifiedByName = "formatLocalDateTime")
    public abstract BlogDto blogToDto(Blogs dto);

    public abstract CustomerSupports toEntity(CustomerSupportDto dto, @MappingTarget CustomerSupports entity);

    public abstract CustomerSupportDto customerSupportToDto(CustomerSupports dto);

    public abstract List<CustomerSupportDto> customerSupportsToDto(List<CustomerSupports> dto);

    public abstract PartnerShip toEntity(PartnerShipDto dto, @MappingTarget PartnerShip entity);

    public abstract List<PartnerShipDto> partnerShipToDto(List<PartnerShip> dto);

    @Mapping(target = "imageIcon", source = "iconImageId", qualifiedByName = "getImageById")
    @Mapping(target = "image", source = "imageId", qualifiedByName = "getImageById")
    public abstract PartnerShipDto partnerShipToDto(PartnerShip dto);

    @Named("formatLocalDateTime")
    public String formatLocalDateTime(LocalDateTime date) {
        return date.getMonth().toString().concat(" ").concat(String.valueOf(date.getDayOfMonth())).concat(", ")
                .concat(String.valueOf(date.getYear()));
    }

    @Named("getImageById")
    public String getImageById(Long id) {
        Optional<Image> imageOpt = imageRepository.findById(id);
        if (imageOpt.isPresent()) {
            return imageOpt.get().getImageContent();
        } else {
            return " ";
        }
    }
}
