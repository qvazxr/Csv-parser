package ru.shift.userimporter.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.shift.userimporter.api.dto.ClientResponseDto;
import ru.shift.userimporter.api.mapper.ClientsMapper;
import ru.shift.userimporter.core.model.Users;
import ru.shift.userimporter.core.repository.UsersRepository;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class UserController {

    private final UsersRepository usersRepository;
    private final ClientsMapper clientsMapper;

    public UserController(UsersRepository usersRepository,
                          ClientsMapper clientsMapper) {
        this.usersRepository = usersRepository;
        this.clientsMapper = clientsMapper;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getClients(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "100") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset) {

        List<Users> users;

        if (phone != null) {
            users = usersRepository.findByPhone(phone)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        } else if (email != null) {
            users = usersRepository.findByEmail(email)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        } else {
            users = usersRepository.findAll();
        }

        int from = Math.min(offset, users.size());
        int to = Math.min(offset + limit, users.size());
        List<Users> paginated = users.subList(from, to);

        List<ClientResponseDto> dtos = clientsMapper.toDtoList(paginated);
        return ResponseEntity.ok(dtos);
    }
}