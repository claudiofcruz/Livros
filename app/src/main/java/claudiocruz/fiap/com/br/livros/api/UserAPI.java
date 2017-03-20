package claudiocruz.fiap.com.br.livros.api;

import java.util.List;

import claudiocruz.fiap.com.br.livros.model.User;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by claudiocruz on 19/03/17.
 */

public interface UserAPI {

    @GET("v2/58b9b1740f0000b614f09d2f/")
    Call<User> findBy();

}
