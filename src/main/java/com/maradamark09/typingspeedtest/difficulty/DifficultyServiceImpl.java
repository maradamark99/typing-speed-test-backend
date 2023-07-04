package com.maradamark09.typingspeedtest.difficulty;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultyServiceImpl implements DifficultyService {

    private final DifficultyMapper mapper;

    private final DifficultyRepository difficultyRepository;

    @Override
    public List<DifficultyDTO> findAll() {
        return difficultyRepository
                .findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public DifficultyDTO save(DifficultyDTO difficultyDTO) {
        if (difficultyRepository.existsByValue(difficultyDTO.getValue()))
            throw new DifficultyAlreadyExistsException(difficultyDTO.getValue());
        return mapper.entityToDto(difficultyRepository.save(mapper.dtoToEntity(difficultyDTO)));
    }

    @Override
    public void deleteById(long id) {
        if (!difficultyRepository.existsById(id))
            throw new DifficultyNotFoundException(id);
        difficultyRepository.deleteById(id);
    }

    @Override
    public DifficultyDTO update(DifficultyDTO difficultyDTO, long id) {
        if (!difficultyRepository.existsById(id))
            throw new DifficultyNotFoundException(id);
        return mapper.entityToDto(difficultyRepository.save(mapper.dtoToEntity(difficultyDTO)));
    }

}
