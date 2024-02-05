package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.VerificationCodeRequest;
import ma.youcode.myrhbackendapi.dto.responses.UserResponse;
import ma.youcode.myrhbackendapi.entities.User;
import ma.youcode.myrhbackendapi.entities.VerificationCode;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.UserRepository;
import ma.youcode.myrhbackendapi.services.UserService;
import ma.youcode.myrhbackendapi.services.VerificationCodeService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("No User Found with Email: " + username));
    }
}
