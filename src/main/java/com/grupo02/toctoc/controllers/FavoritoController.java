package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.Favorito;
import com.grupo02.toctoc.models.Like;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.repository.db.FavoriteRepository;
import com.grupo02.toctoc.repository.db.PostRepository;
import com.grupo02.toctoc.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/favorite")
public class FavoritoController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/{postId}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity addOrDeleteFav(@PathVariable String postId) {

        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);

        if (userAuth.isPresent()) {
            Favorito favorito = favoriteRepository.findByUser(userAuth.get());

            if (favorito == null) {
                favorito = new Favorito();
                favorito.setUser(userAuth.get());
            }

            Post post = postRepository.findById(UUID.fromString(postId)).orElseThrow();

            if (favorito.getFavoritos().contains(post)) {
                favorito.getFavoritos().remove(post);
            } else {
                favorito.getFavoritos().add(post);
            }

            favoriteRepository.save(favorito);

            return ResponseEntity.ok().build();
        }
        throw new RuntimeException("User not authenticated");
    }

    @GetMapping()
    @SecurityRequirement(name = "bearer")
    public ResponseEntity getFav() {

        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);

        if (userAuth.isPresent()) {

            Favorito favorito = favoriteRepository.findByUser(userAuth.get());

            return ResponseEntity.ok(favorito.getFavoritos());
        }
        throw new RuntimeException("User not authenticated");

    }
}
