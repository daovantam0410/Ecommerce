package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final int USER_PER_PAGE = 4;

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    List<User> listAll(){
        //Add sort method when export file csv data have will arrange ascending
        return (List<User>) userRepository.findAll(Sort.by("firstName").ascending());
    }

    /*
    Pagination
    */
    public Page<User> listByPage(int pageNumber, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1,  USER_PER_PAGE, sort);

        //Check keyword != null
        if (keyword != null){
            return userRepository.findAll(keyword, pageable);
        }
        return userRepository.findAll(pageable);
    }

    public List<Role> listRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    public User save(User user){
        //Get ID user is NOT NULL
        boolean isUpdatingUser = (user.getId() != null);

        //ID NOT NULL -> user đang update
        if (isUpdatingUser){
            User existingUser = userRepository.findById(user.getId()).get();
            //Nếu password người dùng trong form isEmpty
            if (user.getPassword().isEmpty()){
                //New password = old password
                user.setPassword(existingUser.getPassword());
            }
            //Nếu password người dùng trong form NOT isEmpty
            else {
                encodePassword(user); //Mã hóa password
            }
        }
        //ID NULL
        else {
            encodePassword(user);
        }

        return userRepository.save(user);
    }

    public User updateAccount(User userInForm){
        User user = userRepository.findById(userInForm.getId()).get();

        if (!userInForm.getPassword().isEmpty()){
            user.setPassword(userInForm.getPassword());
            encodePassword(user);
        }
        if (userInForm.getPhotos() != null){
            user.setPhotos(userInForm.getPhotos());
        }

        user.setFirstName(userInForm.getFirstName());
        user.setLastName(userInForm.getLastName());

        return userRepository.save(user);
    }

    private void encodePassword(User user){
        //Mã hóa mật khẩu của user
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        //User sẽ cập nhật lại password mới với giá trị đã được mã hóa
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email){
        User userByEmail = userRepository.getUserByEmail(email);

        //Check email người dùng tồn tại tại không
        if (userByEmail == null)
            return true; //Email là duy nhất

        //Check ID chưa tồn tại, đồng nghĩa người dùng đang thêm mới
        boolean isCreateingNew = (id == null);

        //Nếu ID user chưa tồn tại
        if (isCreateingNew){
            //Nếu email đã tồn tại
            if (userByEmail != null) return false; //Không cho user submit email đó
        }
        //Nếu ID tồn tại, đồng nghĩa người dùng đang sửa email
        else {
            //Kiểm tra email đã trùng với id người khác chưa
            if (userByEmail.getId() != id){
                return false; //Email này không phải là duy nhất
            }
        }

        return true;
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new UserNotFoundException("Could not found any user with ID: " + id);
        }

    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0){
            throw new UserNotFoundException("Could not found any user with ID: " + id);
        }

        userRepository.deleteById(id);
    }

    /*
    Update user enabled status
    */
    public void updateUserEnabledStatus(Integer id, boolean enabled){
        userRepository.updateEnabledStatus(id, enabled);
    }
}
