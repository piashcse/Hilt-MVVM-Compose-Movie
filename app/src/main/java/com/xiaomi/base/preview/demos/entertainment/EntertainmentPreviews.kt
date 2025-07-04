package com.xiaomi.base.preview.demos.entertainment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.preview.catalog.PreviewCategory
import com.xiaomi.base.preview.catalog.PreviewDifficulty
import com.xiaomi.base.preview.catalog.PreviewItem
import com.xiaomi.base.preview.catalog.PreviewRegistry
import com.xiaomi.base.ui.theme.BaseAppTheme

// Data classes for Entertainment demos
data class Song(
    val title: String,
    val artist: String,
    val album: String,
    val duration: String,
    val albumArt: Color,
    val isPlaying: Boolean = false,
    val isFavorite: Boolean = false
)

data class Playlist(
    val name: String,
    val songCount: Int,
    val duration: String,
    val coverArt: Color,
    val isPublic: Boolean = true
)

data class Movie(
    val title: String,
    val genre: String,
    val year: String,
    val rating: String,
    val duration: String,
    val poster: Color,
    val isWatchlisted: Boolean = false
)

data class TVShow(
    val title: String,
    val genre: String,
    val seasons: Int,
    val episodes: Int,
    val rating: String,
    val poster: Color,
    val isWatching: Boolean = false
)

data class Game(
    val title: String,
    val genre: String,
    val rating: String,
    val price: String,
    val platform: String,
    val cover: Color,
    val isOwned: Boolean = false
)

data class Podcast(
    val title: String,
    val host: String,
    val category: String,
    val duration: String,
    val episodeNumber: Int,
    val cover: Color,
    val isSubscribed: Boolean = false
)

data class LiveStream(
    val title: String,
    val streamer: String,
    val category: String,
    val viewers: String,
    val thumbnail: Color,
    val isLive: Boolean = true
)

// Music Player Preview
@Preview(name = "Music Player", showBackground = true)
@Composable
fun MusicPlayerPreview() {
    BaseAppTheme {
        MusicPlayer()
    }
}

@Composable
fun MusicPlayer() {
    val currentSong = Song(
        title = "Bohemian Rhapsody",
        artist = "Queen",
        album = "A Night at the Opera",
        duration = "5:55",
        albumArt = Color(0xFF9C27B0),
        isPlaying = true,
        isFavorite = true
    )
    
    val recentSongs = listOf(
        Song("Hotel California", "Eagles", "Hotel California", "6:30", Color(0xFF2196F3)),
        Song("Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV", "8:02", Color(0xFF4CAF50)),
        Song("Sweet Child O' Mine", "Guns N' Roses", "Appetite for Destruction", "5:03", Color(0xFFFF9800)),
        Song("Imagine", "John Lennon", "Imagine", "3:07", Color(0xFFF44336))
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Music Player",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            NowPlayingCard(currentSong)
        }
        
        item {
            PlayerControls()
        }
        
        item {
            Text(
                text = "Recently Played",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(recentSongs) { song ->
            SongItem(song)
        }
    }
}

// Video Streaming Preview
@Preview(name = "Video Streaming", showBackground = true)
@Composable
fun VideoStreamingPreview() {
    BaseAppTheme {
        VideoStreaming()
    }
}

@Composable
fun VideoStreaming() {
    val featuredMovies = listOf(
        Movie("The Matrix", "Sci-Fi", "1999", "8.7", "2h 16m", Color(0xFF2196F3)),
        Movie("Inception", "Thriller", "2010", "8.8", "2h 28m", Color(0xFF9C27B0)),
        Movie("Interstellar", "Sci-Fi", "2014", "8.6", "2h 49m", Color(0xFF4CAF50))
    )
    
    val tvShows = listOf(
        TVShow("Breaking Bad", "Drama", 5, 62, "9.5", Color(0xFFFF9800)),
        TVShow("Game of Thrones", "Fantasy", 8, 73, "9.3", Color(0xFFF44336)),
        TVShow("The Office", "Comedy", 9, 201, "9.0", Color(0xFF607D8B))
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Video Streaming",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Text(
                text = "Featured Movies",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(featuredMovies) { movie ->
                    MovieCard(movie)
                }
            }
        }
        
        item {
            Text(
                text = "Popular TV Shows",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(tvShows) { show ->
            TVShowItem(show)
        }
    }
}

// Gaming Hub Preview
@Preview(name = "Gaming Hub", showBackground = true)
@Composable
fun GamingHubPreview() {
    BaseAppTheme {
        GamingHub()
    }
}

@Composable
fun GamingHub() {
    val featuredGames = listOf(
        Game("Cyberpunk 2077", "RPG", "8.5", "$59.99", "PC", Color(0xFFE91E63)),
        Game("The Witcher 3", "RPG", "9.3", "$39.99", "PC", Color(0xFF9C27B0)),
        Game("Red Dead Redemption 2", "Action", "9.7", "$49.99", "PC", Color(0xFF795548))
    )
    
    val myGames = listOf(
        Game("Minecraft", "Sandbox", "9.0", "Owned", "PC", Color(0xFF4CAF50), true),
        Game("Among Us", "Social", "8.2", "Owned", "Mobile", Color(0xFFF44336), true),
        Game("Fall Guys", "Party", "7.8", "Owned", "PC", Color(0xFFFF9800), true)
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Gaming Hub",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            GamingStatsRow()
        }
        
        item {
            Text(
                text = "Featured Games",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(featuredGames) { game ->
                    GameCard(game)
                }
            }
        }
        
        item {
            Text(
                text = "My Games",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(myGames) { game ->
            GameItem(game)
        }
    }
}

// Podcast Player Preview
@Preview(name = "Podcast Player", showBackground = true)
@Composable
fun PodcastPlayerPreview() {
    BaseAppTheme {
        PodcastPlayer()
    }
}

@Composable
fun PodcastPlayer() {
    val currentPodcast = Podcast(
        title = "The Future of AI",
        host = "Tech Talk",
        category = "Technology",
        duration = "45:30",
        episodeNumber = 127,
        cover = Color(0xFF2196F3),
        isSubscribed = true
    )
    
    val recentPodcasts = listOf(
        Podcast("Startup Stories", "Business Weekly", "Business", "32:15", 89, Color(0xFF4CAF50)),
        Podcast("Health & Wellness", "Dr. Smith", "Health", "28:45", 156, Color(0xFFFF9800)),
        Podcast("Comedy Hour", "Laugh Track", "Comedy", "55:20", 234, Color(0xFFF44336))
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Podcast Player",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            NowPlayingPodcastCard(currentPodcast)
        }
        
        item {
            PodcastControls()
        }
        
        item {
            Text(
                text = "Recent Episodes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(recentPodcasts) { podcast ->
            PodcastItem(podcast)
        }
    }
}

// Live Streaming Preview
@Preview(name = "Live Streaming", showBackground = true)
@Composable
fun LiveStreamingPreview() {
    BaseAppTheme {
        LiveStreaming()
    }
}

@Composable
fun LiveStreaming() {
    val liveStreams = listOf(
        LiveStream("Gaming Marathon", "ProGamer123", "Gaming", "12.5K", Color(0xFF9C27B0)),
        LiveStream("Cooking Show", "ChefMaster", "Cooking", "8.2K", Color(0xFF4CAF50)),
        LiveStream("Music Session", "MusicLover", "Music", "5.7K", Color(0xFF2196F3)),
        LiveStream("Art Tutorial", "ArtistPro", "Art", "3.1K", Color(0xFFFF9800))
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Live Streaming",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            StreamingStatsRow()
        }
        
        item {
            Text(
                text = "Live Now",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        items(liveStreams) { stream ->
            LiveStreamCard(stream)
        }
    }
}

// Helper Composables
@Composable
fun NowPlayingCard(song: Song) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = song.albumArt,
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "Album Art",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = song.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                
                Text(
                    text = song.album,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                )
            }
            
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = if (song.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (song.isFavorite) Color.Red else MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun PlayerControls() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Progress bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "2:34",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                LinearProgressIndicator(
                    progress = 0.43f,
                    modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
                )
                
                Text(
                    text = "5:55",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Control buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = "Shuffle"
                    )
                }
                
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                FloatingActionButton(
                    onClick = { },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Pause,
                        contentDescription = "Play/Pause",
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Repeat,
                        contentDescription = "Repeat"
                    )
                }
            }
        }
    }
}

@Composable
fun SongItem(song: Song) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = song.albumArt,
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "Album Art",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = song.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = song.duration,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        IconButton(
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options"
            )
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Card(
        modifier = Modifier.width(160.dp)
    ) {
        Column {
            Surface(
                color = movie.poster,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Movie,
                        contentDescription = movie.title,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "${movie.genre} • ${movie.year}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(
                        text = movie.rating,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun TVShowItem(show: TVShow) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = show.poster,
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Tv,
                        contentDescription = show.title,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = show.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = show.genre,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "${show.seasons} seasons • ${show.episodes} episodes",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(
                        text = show.rating,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                if (show.isWatching) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "Watching",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GamingStatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GamingStatCard(
            title = "Games Owned",
            value = "47",
            icon = Icons.Default.SportsEsports,
            color = MaterialTheme.colorScheme.primary
        )
        
        GamingStatCard(
            title = "Hours Played",
            value = "1,247",
            icon = Icons.Default.Schedule,
            color = Color(0xFF4CAF50)
        )
        
        GamingStatCard(
            title = "Achievements",
            value = "89",
            icon = Icons.Default.EmojiEvents,
            color = Color(0xFFFF9800)
        )
    }
}

@Composable
fun GamingStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Card {
        // Note: weight should only be used within RowScope or ColumnScope
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun GameCard(game: Game) {
    Card(
        modifier = Modifier.width(160.dp)
    ) {
        Column {
            Surface(
                color = game.cover,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SportsEsports,
                        contentDescription = game.title,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = game.genre,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = game.price,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(4.dp))
                        
                        Text(
                            text = game.rating,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GameItem(game: Game) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = game.cover,
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SportsEsports,
                        contentDescription = game.title,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = "${game.genre} • ${game.platform}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (game.isOwned) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF4CAF50).copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "Owned",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            } else {
                Button(
                    onClick = { },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Buy",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun NowPlayingPodcastCard(podcast: Podcast) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = podcast.cover,
                modifier = Modifier.size(64.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Podcasts,
                        contentDescription = "Podcast Cover",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Episode ${podcast.episodeNumber}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                
                Text(
                    text = podcast.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = podcast.host,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
            
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = if (podcast.isSubscribed) Icons.Default.Notifications else Icons.Default.NotificationsNone,
                    contentDescription = "Subscribe",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun PodcastControls() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Progress bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "12:45",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                LinearProgressIndicator(
                    progress = 0.28f,
                    modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
                )
                
                Text(
                    text = "45:30",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Control buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Replay10,
                        contentDescription = "Replay 10s"
                    )
                }
                
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                FloatingActionButton(
                    onClick = { },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Pause,
                        contentDescription = "Play/Pause",
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Forward30,
                        contentDescription = "Forward 30s"
                    )
                }
            }
        }
    }
}

@Composable
fun PodcastItem(podcast: Podcast) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = podcast.cover,
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Podcasts,
                    contentDescription = "Podcast Cover",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Episode ${podcast.episodeNumber}: ${podcast.title}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = "${podcast.host} • ${podcast.category}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = podcast.duration,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        IconButton(
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play"
            )
        }
    }
}

@Composable
fun StreamingStatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StreamingStatCard(
            title = "Live Streams",
            value = "1,247",
            color = Color(0xFFF44336)
        )
        
        StreamingStatCard(
            title = "Total Viewers",
            value = "89.2K",
            color = Color(0xFF4CAF50)
        )
        
        StreamingStatCard(
            title = "Following",
            value = "156",
            color = Color(0xFF2196F3)
        )
    }
}

@Composable
fun StreamingStatCard(
    title: String,
    value: String,
    color: Color
) {
    Card {
        // Note: weight should only be used within RowScope or ColumnScope
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun LiveStreamCard(stream: LiveStream) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box {
                Surface(
                    color = stream.thumbnail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play Stream",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                
                if (stream.isLive) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.Red,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Text(
                            text = "LIVE",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.Black.copy(alpha = 0.7f),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = stream.viewers,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stream.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stream.streamer,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = stream.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

// Register previews with PreviewRegistry
fun registerEntertainmentPreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "music_player",
            title = "Music Player",
            description = "Complete music player interface with now playing, controls, and playlist management",
            category = PreviewCategory.ENTERTAINMENT,
            icon = Icons.Default.MusicNote,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "30 minutes",
            tags = listOf("music", "player", "audio", "playlist", "controls"),
            content = { MusicPlayerPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "video_streaming",
            title = "Video Streaming",
            description = "Video streaming platform with movies, TV shows, and content discovery",
            category = PreviewCategory.ENTERTAINMENT,
            icon = Icons.Default.Movie,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "35 minutes",
            tags = listOf("video", "streaming", "movies", "tv shows", "entertainment"),
            content = { VideoStreamingPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "gaming_hub",
            title = "Gaming Hub",
            description = "Gaming platform with game library, statistics, and game discovery",
            category = PreviewCategory.ENTERTAINMENT,
            icon = Icons.Default.SportsEsports,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "40 minutes",
            tags = listOf("gaming", "games", "library", "statistics", "platform"),
            content = { GamingHubPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "podcast_player",
            title = "Podcast Player",
            description = "Podcast player with episode management, playback controls, and subscription features",
            category = PreviewCategory.ENTERTAINMENT,
            icon = Icons.Default.Podcasts,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "25 minutes",
            tags = listOf("podcast", "audio", "episodes", "subscription", "player"),
            content = { PodcastPlayerPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "live_streaming",
            title = "Live Streaming",
            description = "Live streaming platform with real-time content, viewer statistics, and category browsing",
            category = PreviewCategory.ENTERTAINMENT,
            icon = Icons.Default.LiveTv,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "35 minutes",
            tags = listOf("live", "streaming", "real-time", "viewers", "broadcast"),
            content = { LiveStreamingPreview() }
        )
    )
}