//package co.oudom.ecommerce.init;
//
//import co.oudom.ecommerce.domain.Category;
//import co.oudom.ecommerce.domain.Role;
//import co.oudom.ecommerce.domain.User;
//import co.oudom.ecommerce.features.category.CategoryRepository;
//import co.oudom.ecommerce.features.user.RoleRepository;
//import co.oudom.ecommerce.features.user.UserRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class DataInit {
//
//    private final RoleRepository roleRepository;
//    private final UserRepository userRepository;
//    private final CategoryRepository categoryRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    void init() {
//
//        Category category = new Category();
//        category.setUuid(UUID.randomUUID().toString());
//        category.setImage("image");
//        category.setName("Controller");
//
//        Category category1 = new Category();
//        category1.setUuid(UUID.randomUUID().toString());
//        category1.setImage("image1");
//        category1.setName("Nintendo");
//
//        Category category2 = new Category();
//        category2.setUuid(UUID.randomUUID().toString());
//        category2.setName("Accessories");
//        category2.setImage("image2");
//        categoryRepository.saveAll(List.of(category, category1, category2));
//
//        Role user = new Role();
//        user.setName("USER");
//
//        Role customer = new Role();
//        customer.setName("CUSTOMER");
//
//        Role manager = new Role();
//        manager.setName("MANAGER");
//
//        Role admin = new Role();
//        admin.setName("ADMIN");
//
//        roleRepository.saveAll(List.of(user,customer, manager, admin));
//
//        User user1 = new User();
//        user1.setUuid(UUID.randomUUID().toString());
//        user1.setFirstName("Chin");
//        user1.setLastName("Oudom");
//        user1.setEmail("admin@gmail.com");
//        user1.setPassword(passwordEncoder.encode("admin"));
//        user1.setProfileImage("image/admin.jpg");
//        user1.setIsVerified(true);
//        user1.setRoles(List.of(user,admin));
//
//        User user2 = new User();
//        user2.setUuid(UUID.randomUUID().toString());
//        user2.setFirstName("San");
//        user2.setLastName("Neasa");
//        user2.setEmail("manager@gmail.com");
//        user2.setPassword(passwordEncoder.encode("manager"));
//        user2.setProfileImage("image/manager.jpg");
//        user2.setIsVerified(true);
//        user2.setRoles(List.of(user,manager));
//
//        User user3 = new User();
//        user3.setUuid(UUID.randomUUID().toString());
//        user3.setFirstName("San");
//        user3.setLastName("Sunly");
//        user3.setEmail("customer@gmail.com");
//        user3.setPassword(passwordEncoder.encode("customer"));
//        user3.setProfileImage("image/user.jpg");
//        user3.setIsVerified(true);
//        user3.setRoles(List.of(user,customer));
//
//        userRepository.saveAll(List.of(user1, user2, user3));
//
//    }
//
//}
