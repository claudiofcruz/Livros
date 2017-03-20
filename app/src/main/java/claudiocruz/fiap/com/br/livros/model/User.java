package claudiocruz.fiap.com.br.livros.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by claudiocruz on 19/03/17.
 */

public class User implements Parcelable {


    // Properties
    public final static String NOME_TABELA  = "TB_USUARIO";
    public final static String NAME  = "NAME";
    public final static String PASSWORD  = "PASSWORD";

    @SerializedName("usuario")
    private String name;

    @SerializedName("senha")
    private String password;


    // Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Costructor
    protected User(Parcel in) {
        name = in.readString();
        password = in.readString();
    }

    // Methods
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
