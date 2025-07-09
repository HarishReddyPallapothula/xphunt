package studio.contrarian.xphunt.app.service;

import studio.contrarian.xphunt.app.dto.HunterDTO;
import studio.contrarian.xphunt.app.dto.UpdateHunterRequest;

public interface HunterService {
    HunterDTO getHunterDetails(Long hunterId);
    HunterDTO updateHunter(Long hunterId, UpdateHunterRequest request);
}