package studio.contrarian.xphunt.app.service;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.contrarian.xphunt.app.dto.HunterDTO;
import studio.contrarian.xphunt.app.dto.UpdateHunterRequest;
import studio.contrarian.xphunt.app.mappers.HunterMapper;
import studio.contrarian.xphunt.app.model.Hunter;
import studio.contrarian.xphunt.app.repo.HunterRepository;

@Service

public class HunterServiceImpl implements HunterService {

    HunterRepository hunterRepository;

    public HunterServiceImpl(HunterRepository hunterRepository) {
        this.hunterRepository = hunterRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public HunterDTO getHunterDetails(Long hunterId) {
        Hunter hunter = hunterRepository.findById(hunterId)
                .orElseThrow(() -> new EntityNotFoundException("Hunter not found with id: " + hunterId));
        return HunterMapper.toDTO(hunter);
    }

    @Override
    @Transactional
    public HunterDTO updateHunter(Long hunterId, UpdateHunterRequest request) {
        Hunter hunter = hunterRepository.findById(hunterId)
                .orElseThrow(() -> new EntityNotFoundException("Hunter not found with id: " + hunterId));

        // Optional: Check if the new name is already taken by another user
        if (request.getName() != null && !request.getName().equals(hunter.getName())) {
            if(hunterRepository.findByName(request.getName()).isPresent()) {
                throw new IllegalStateException("Name is already taken.");
            }
            hunter.setName(request.getName());
        }
        
        Hunter updatedHunter = hunterRepository.save(hunter);
        return HunterMapper.toDTO(updatedHunter);
    }
}