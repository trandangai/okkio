package io.okkio.services.impl;

import io.okkio.common.EntityStatus;
import io.okkio.common.ImageType;
import io.okkio.domain.*;
import io.okkio.dto.BlogDto;
import io.okkio.dto.CareerDto;
import io.okkio.dto.CustomerSupportDto;
import io.okkio.dto.PartnerShipDto;
import io.okkio.mapper.DiscoveryMapper;
import io.okkio.repository.*;
import io.okkio.services.DiscoveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DiscoveryServiceImpl implements DiscoveryService {

    @Autowired
    private BlogsRepository blogsRepository;
    @Autowired
    private CareersRepository careersRepository;
    @Autowired
    private CustomerSupportRepository customerSupportRepository;
    @Autowired
    private PartnerShipRepository partnerShipRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private DiscoveryMapper discoveryMapper;

    @Override
    public List<CareerDto> getAllCareers() {
        List<Careers> careerList = careersRepository.findAll();
        List<CareerDto> careerDtos = discoveryMapper.careersToDto(careerList);
        return careerDtos;
    }

    @Override
    public List<BlogDto> getAllBlogs() {
        try {
            List<Blogs> blogsList = blogsRepository.findAll();
            List<BlogDto> blogDtos = discoveryMapper.blogsToDto(blogsList);
            return blogDtos;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<PartnerShipDto> getAllPartnerShip() {
        try {
            List<PartnerShip> partnerShipsList = partnerShipRepository.findAll();
            List<PartnerShipDto> partnerShipDtos = discoveryMapper.partnerShipToDto(partnerShipsList);
            return partnerShipDtos;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<CustomerSupportDto> getAllCustomerSupports() {
        try {
            List<CustomerSupports> customerSupportsList = customerSupportRepository.findAll();
            List<CustomerSupportDto> customerSupportDtos = discoveryMapper.customerSupportsToDto(customerSupportsList);
            return customerSupportDtos;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CareerDto addNewCareer(CareerDto dto) {
        try {
            Careers newEntity = discoveryMapper.toEntity(dto, new Careers());
            newEntity.setStatus(EntityStatus.ACTIVATED);
            newEntity = careersRepository.save(newEntity);
            return discoveryMapper.careerToDto(newEntity);
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public BlogDto addNewBlog(BlogDto dto) {
        try {
            Image imageSaved = new Image();
            imageSaved.setStatus(EntityStatus.ACTIVATED);
            imageSaved.setImageContent(dto.getImage());
            imageSaved.setType(ImageType.DISCOVERY_BLOG);
            imageSaved = imageRepository.save(imageSaved);

            Blogs newEntity = discoveryMapper.toEntity(dto, new Blogs());
            newEntity.setImageId(imageSaved.getId());
            newEntity.setStatus(EntityStatus.ACTIVATED);
            newEntity = blogsRepository.save(newEntity);
            return discoveryMapper.blogToDto(newEntity);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PartnerShipDto addNewPartnerShip(PartnerShipDto dto) {
        try {
            Image imageIconSaved = new Image();
            imageIconSaved.setStatus(EntityStatus.ACTIVATED);
            imageIconSaved.setImageContent(dto.getImageIcon());
            imageIconSaved.setType(ImageType.DISCOVERY_PARTNERSHIP_ICON);
            imageIconSaved = imageRepository.save(imageIconSaved);

            Image imageSaved = new Image();
            imageSaved.setStatus(EntityStatus.ACTIVATED);
            imageSaved.setImageContent(dto.getImage());
            imageSaved.setType(ImageType.DISCOVERY_PARTNERSHIP_PICTURE);
            imageSaved = imageRepository.save(imageSaved);

            PartnerShip newEntity = discoveryMapper.toEntity(dto, new PartnerShip());
            newEntity.setImageId(imageSaved.getId());
            newEntity.setIconImageId(imageIconSaved.getId());
            newEntity.setStatus(EntityStatus.ACTIVATED);
            newEntity = partnerShipRepository.save(newEntity);
            return discoveryMapper.partnerShipToDto(newEntity);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CustomerSupportDto addNewCustomerSupport(CustomerSupportDto dto) {
        try {
            CustomerSupports newEntity = discoveryMapper.toEntity(dto, new CustomerSupports());
            newEntity.setStatus(EntityStatus.ACTIVATED);
            newEntity = customerSupportRepository.save(newEntity);
            return discoveryMapper.customerSupportToDto(newEntity);
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Careers getCareerById(Long id) {
        Optional<Careers> location = careersRepository.findById(id);
        if (!location.isPresent()) {
            log.warn("DiscoveryServiceImpl - getCareerById null with id: " + id);
            return null;
        }
        return location.get();
    }

    @Override
    public Blogs getBlogById(Long id) {
        Optional<Blogs> location = blogsRepository.findById(id);
        if (!location.isPresent()) {
            log.warn("DiscoveryServiceImpl - getBlogById null with id: " + id);
            return null;
        }
        return location.get();
    }

    @Override
    public PartnerShip getPartnerShipById(Long id) {
        Optional<PartnerShip> location = partnerShipRepository.findById(id);
        if (!location.isPresent()) {
            log.warn("DiscoveryServiceImpl - getPartnerShipById null with id: " + id);
            return null;
        }
        return location.get();
    }

    @Override
    public CustomerSupports getCustomerSupportsById(Long id) {
        Optional<CustomerSupports> location = customerSupportRepository.findById(id);
        if (!location.isPresent()) {
            log.warn("DiscoveryServiceImpl - getCustomerSupportsById null with id: " + id);
            return null;
        }
        return location.get();
    }

    @Override
    public CareerDto updateCareer(CareerDto dto) {
        Long id = dto.getId();
        try {
            Careers updatedEntity = discoveryMapper.toEntity(dto, getCareerById(id));
            updatedEntity = careersRepository.save(updatedEntity);
            return discoveryMapper.careerToDto(updatedEntity);
        } catch (Exception e) {
            log.warn("DiscoveryServiceImpl - updateCareer got exception:" + e.getMessage() + "  with id: " + id);
            return null;
        }
    }

    @Override
    public BlogDto updateBlog(BlogDto dto) {
        Long id = dto.getId();
        try {
            Blogs entitySaved = getBlogById(id);
            Optional<Image> imageOpt = imageRepository.findById(entitySaved.getImageId());
            if (!imageOpt.isPresent()) {
                throw new Exception("Can not find image with blog enity " + entitySaved.getId());
            }
            Image image = imageOpt.get();
            image.setImageContent(dto.getImage());
            imageRepository.save(image);
            Blogs updatedEntity = discoveryMapper.toEntity(dto, entitySaved);
            updatedEntity = blogsRepository.save(updatedEntity);
            return discoveryMapper.blogToDto(updatedEntity);
        } catch (Exception e) {
            log.warn("DiscoveryServiceImpl - updateBlog got exception:" + e.getMessage() + "  with id: " + id);
            return null;
        }
    }

    @Override
    public PartnerShipDto updatePartnerShip(PartnerShipDto dto) {
        Long id = dto.getId();
        try {
            PartnerShip entitySaved = getPartnerShipById(id);
            Optional<Image> imageIconOpt = imageRepository.findById(entitySaved.getIconImageId());
            if (!imageIconOpt.isPresent()) {
                throw new Exception("Can not find icon image with partner ship enity " + entitySaved.getId());
            }
            Image imageIcon = imageIconOpt.get();
            imageIcon.setImageContent(dto.getImage());
            imageRepository.save(imageIcon);

            Optional<Image> imageOpt = imageRepository.findById(entitySaved.getImageId());
            if (!imageOpt.isPresent()) {
                throw new Exception("Can not find image with partner ship enity " + entitySaved.getId());
            }
            Image image = imageOpt.get();
            image.setImageContent(dto.getImage());
            imageRepository.save(image);

            PartnerShip updatedEntity = discoveryMapper.toEntity(dto, getPartnerShipById(id));
            updatedEntity = partnerShipRepository.save(updatedEntity);
            return discoveryMapper.partnerShipToDto(updatedEntity);
        } catch (Exception e) {
            log.warn("DiscoveryServiceImpl - updatePartnerShip got exception:" + e.getMessage() + "  with id: " + id);
            return null;
        }
    }

    @Override
    public CustomerSupportDto updateCustomerSupports(CustomerSupportDto dto) {
        Long id = dto.getId();
        try {
            CustomerSupports updatedEntity = discoveryMapper.toEntity(dto, getCustomerSupportsById(id));
            updatedEntity = customerSupportRepository.save(updatedEntity);
            return discoveryMapper.customerSupportToDto(updatedEntity);
        } catch (Exception e) {
            log.warn("DiscoveryServiceImpl - updateCustomerSupports got exception:" + e.getMessage() + "  with id: "
                    + id);
            return null;
        }
    }

}
