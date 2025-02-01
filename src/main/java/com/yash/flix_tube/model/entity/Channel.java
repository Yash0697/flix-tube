package com.yash.flix_tube.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name="channels")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long channelId;
    private String channelName;
    private String channelDescription;
    private String channelUrl;
    private String channelImageUrl;
    private String channelCreationDate;
    private String channelEmail;
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos;

    public Channel(long id, String name) {
        this.channelId = id;
        this.channelName = name;
    }
}
