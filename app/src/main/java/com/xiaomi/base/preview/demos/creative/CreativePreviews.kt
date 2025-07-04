package com.xiaomi.base.preview.demos.creative

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.xiaomi.base.preview.base.BasePreviewScreen
import com.xiaomi.base.preview.base.ComponentShowcase
import com.xiaomi.base.preview.base.InteractiveDemo
import com.xiaomi.base.preview.catalog.PreviewCategory
import com.xiaomi.base.preview.catalog.PreviewDifficulty
import com.xiaomi.base.preview.catalog.PreviewItem
import com.xiaomi.base.preview.catalog.PreviewRegistry
import com.xiaomi.base.ui.theme.BaseTheme
import kotlin.math.*

// Register Creative previews
fun registerCreativePreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "photo_editor",
            title = "Photo Editor Interface",
            description = "Professional photo editing tools with filters, adjustments, and layers",
            category = PreviewCategory.CREATIVE,
            icon = Icons.Default.PhotoCamera,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("photo", "editor", "filters", "adjustments"),
            estimatedTime = "50 min",
            content = { PhotoEditorPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "drawing_canvas",
            title = "Digital Drawing Canvas",
            description = "Interactive drawing canvas with brush tools, colors, and layers",
            category = PreviewCategory.CREATIVE,
            icon = Icons.Default.Brush,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("drawing", "canvas", "brush", "art"),
            estimatedTime = "45 min",
            content = { DrawingCanvasPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "color_palette",
            title = "Color Palette Generator",
            description = "Generate and manage color palettes for design projects",
            category = PreviewCategory.CREATIVE,
            icon = Icons.Default.Palette,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("color", "palette", "design", "generator"),
            estimatedTime = "30 min",
            content = { ColorPalettePreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "design_templates",
            title = "Design Templates Gallery",
            description = "Browse and customize design templates for various projects",
            category = PreviewCategory.CREATIVE,
            icon = Icons.Default.Dashboard,
            difficulty = PreviewDifficulty.BEGINNER,
            tags = listOf("templates", "design", "gallery", "customization"),
            estimatedTime = "25 min",
            content = { DesignTemplatesPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "typography_studio",
            title = "Typography Studio",
            description = "Font pairing, text styling, and typography design tools",
            category = PreviewCategory.CREATIVE,
            icon = Icons.Default.TextFields,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("typography", "fonts", "text", "styling"),
            estimatedTime = "35 min",
            content = { TypographyStudioPreview() }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PhotoEditorPreview() {
    BaseTheme {
        BasePreviewScreen(
            title = "Photo Editor",
            subtitle = "Professional editing tools"
        ) {
            var selectedTool by remember { mutableStateOf(EditingTool.FILTERS) }
            var brightness by remember { mutableStateOf(0f) }
            var contrast by remember { mutableStateOf(0f) }
            var saturation by remember { mutableStateOf(0f) }
            var selectedFilter by remember { mutableStateOf("Original") }
            
            val filters = listOf("Original", "Vintage", "B&W", "Sepia", "Vivid", "Cool", "Warm")
            
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Image preview area
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // Simulated image with applied effects
                        Canvas(
                            modifier = Modifier
                                .size(250.dp, 200.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            val imageColor = when (selectedFilter) {
                                "Vintage" -> Color(0xFFD4A574)
                                "B&W" -> Color.Gray
                                "Sepia" -> Color(0xFFC4915C)
                                "Vivid" -> Color(0xFF4CAF50)
                                "Cool" -> Color(0xFF2196F3)
                                "Warm" -> Color(0xFFFF9800)
                                else -> Color(0xFF6200EE)
                            }
                            
                            // Apply brightness, contrast, saturation effects
                            val adjustedColor = imageColor.copy(
                                alpha = (1f + brightness * 0.3f).coerceIn(0.3f, 1f)
                            )
                            
                            drawRect(
                                color = adjustedColor,
                                size = size
                            )
                            
                            // Draw some sample shapes to simulate image content
                            drawCircle(
                                color = Color.White.copy(alpha = 0.3f),
                                radius = size.minDimension * 0.2f,
                                center = Offset(size.width * 0.3f, size.height * 0.3f)
                            )
                            
                            drawRect(
                                color = Color.White.copy(alpha = 0.2f),
                                topLeft = Offset(size.width * 0.6f, size.height * 0.5f),
                                size = androidx.compose.ui.geometry.Size(
                                    size.width * 0.3f,
                                    size.height * 0.3f
                                )
                            )
                        }
                        
                        // Overlay controls
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .background(
                                        Color.Black.copy(alpha = 0.5f),
                                        CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ZoomIn,
                                    contentDescription = "Zoom In",
                                    tint = Color.White
                                )
                            }
                            
                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .background(
                                        Color.Black.copy(alpha = 0.5f),
                                        CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ZoomOut,
                                    contentDescription = "Zoom Out",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Tool selection
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(EditingTool.values()) { tool ->
                        FilterChip(
                            onClick = { selectedTool = tool },
                            label = { Text(tool.displayName) },
                            selected = selectedTool == tool,
                            leadingIcon = {
                                Icon(
                                    imageVector = tool.icon,
                                    contentDescription = tool.displayName,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Tool-specific controls
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when (selectedTool) {
                        EditingTool.FILTERS -> {
                            FilterControls(
                                filters = filters,
                                selectedFilter = selectedFilter,
                                onFilterSelected = { selectedFilter = it }
                            )
                        }
                        EditingTool.ADJUSTMENTS -> {
                            AdjustmentControls(
                                brightness = brightness,
                                contrast = contrast,
                                saturation = saturation,
                                onBrightnessChange = { brightness = it },
                                onContrastChange = { contrast = it },
                                onSaturationChange = { saturation = it }
                            )
                        }
                        EditingTool.CROP -> {
                            CropControls()
                        }
                        EditingTool.LAYERS -> {
                            LayerControls()
                        }
                    }
                }
                
                // Bottom action bar
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(
                            onClick = { }
                        ) {
                            Text("Cancel")
                        }
                        
                        Button(
                            onClick = { }
                        ) {
                            Text("Save")
                        }
                        
                        Button(
                            onClick = { }
                        ) {
                            Text("Export")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawingCanvasPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Drawing Canvas",
            description = "Interactive digital art creation"
        ) {
            var selectedBrush by remember { mutableStateOf(BrushType.PEN) }
            var brushSize by remember { mutableStateOf(5f) }
            var selectedColor by remember { mutableStateOf(Color.Black) }
            val paths = remember { mutableStateListOf<DrawPath>() }
            var currentPath by remember { mutableStateOf<DrawPath?>(null) }
            
            val colors = listOf(
                Color.Black, Color.Red, Color.Blue, Color.Green,
                Color.Yellow, Color.Magenta, Color.Cyan, Color.Gray
            )
            
            InteractiveDemo(
                title = "Typography Controls",
                description = "Adjust font settings and preview text styling",
                controls = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { paths.clear() }
                            ) {
                                Text("Clear")
                            }
                            
                            Button(
                                onClick = { if (paths.isNotEmpty()) paths.removeLastOrNull() }
                            ) {
                                Text("Undo")
                            }
                        }
                        
                        Text(
                            text = "Brush Size: ${brushSize.toInt()}px",
                            style = MaterialTheme.typography.labelMedium
                        )
                        
                        Slider(
                            value = brushSize,
                            onValueChange = { brushSize = it },
                            valueRange = 1f..20f,
                            modifier = Modifier.width(200.dp)
                        )
                    }
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Brush selection
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(BrushType.values()) { brush ->
                            FilterChip(
                                onClick = { selectedBrush = brush },
                                label = { Text(brush.displayName) },
                                selected = selectedBrush == brush,
                                leadingIcon = {
                                    Icon(
                                        imageVector = brush.icon,
                                        contentDescription = brush.displayName,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            )
                        }
                    }
                    
                    // Color palette
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(colors) { color ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(color, CircleShape)
                                    .border(
                                        width = if (selectedColor == color) 3.dp else 1.dp,
                                        color = if (selectedColor == color) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.outline
                                        },
                                        shape = CircleShape
                                    )
                                    .pointerInput(color) {
                                        detectDragGestures(
                                            onDragStart = { selectedColor = color }
                                        ) { _, _ -> }
                                    }
                            )
                        }
                    }
                    
                    // Drawing canvas
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(selectedBrush, brushSize, selectedColor) {
                                    detectDragGestures(
                                        onDragStart = { offset ->
                                            currentPath = DrawPath(
                                                path = Path().apply { moveTo(offset.x, offset.y) },
                                                color = selectedColor,
                                                strokeWidth = brushSize,
                                                brushType = selectedBrush
                                            )
                                        },
                                        onDrag = { _, dragAmount ->
                                            currentPath?.let { drawPath ->
                                                val newPath = Path().apply {
                                                    addPath(drawPath.path)
                                                    relativeLineTo(dragAmount.x, dragAmount.y)
                                                }
                                                currentPath = drawPath.copy(path = newPath)
                                            }
                                        },
                                        onDragEnd = {
                                            currentPath?.let { paths.add(it) }
                                            currentPath = null
                                        }
                                    )
                                }
                        ) {
                            // Draw background
                            drawRect(
                                color = Color.White,
                                size = size
                            )
                            
                            // Draw all completed paths
                            paths.forEach { drawPath ->
                                drawPath(drawPath)
                            }
                            
                            // Draw current path
                            currentPath?.let { drawPath ->
                                drawPath(drawPath)
                            }
                        }
                    }
                    
                    // Layer controls
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Layers",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                IconButton(
                                    onClick = { }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add Layer"
                                    )
                                }
                                
                                IconButton(
                                    onClick = { }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Layers,
                                        contentDescription = "Layer Options"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorPalettePreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Color Palette Generator",
            description = "Create and manage color schemes"
        ) {
            var selectedPalette by remember { mutableStateOf(0) }
            var generationType by remember { mutableStateOf(PaletteType.COMPLEMENTARY) }
            
            val palettes = remember {
                listOf(
                    ColorPalette(
                        name = "Ocean Breeze",
                        colors = listOf(
                            Color(0xFF0077BE), Color(0xFF00A8CC), Color(0xFF7FDBDA),
                            Color(0xFFB8E6B8), Color(0xFFFFE66D)
                        )
                    ),
                    ColorPalette(
                        name = "Sunset Glow",
                        colors = listOf(
                            Color(0xFFFF6B6B), Color(0xFFFF8E53), Color(0xFFFF6B9D),
                            Color(0xFFC44569), Color(0xFF6C5CE7)
                        )
                    ),
                    ColorPalette(
                        name = "Forest Path",
                        colors = listOf(
                            Color(0xFF2D5016), Color(0xFF4F7942), Color(0xFF87A96B),
                            Color(0xFFC5D9B7), Color(0xFFF4F4F4)
                        )
                    )
                )
            }
            
            InteractiveDemo(
                title = "Color Palette Generator",
                description = "Generate and customize color palettes",
                controls = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Generation Type:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(PaletteType.values()) { type ->
                                FilterChip(
                                    onClick = { generationType = type },
                                    label = { Text(type.displayName) },
                                    selected = generationType == type
                                )
                            }
                        }
                        
                        Button(
                            onClick = { /* Generate new palette */ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Generate",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate New")
                        }
                    }
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(palettes.size) { index ->
                        val palette = palettes[index]
                        
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedPalette == index) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            ),
                            onClick = { selectedPalette = index }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = palette.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        IconButton(
                                            onClick = { },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Favorite,
                                                contentDescription = "Favorite",
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        
                                        IconButton(
                                            onClick = { },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = "Share",
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                // Color swatches
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    palette.colors.forEach { color ->
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(60.dp)
                                                    .background(color, RoundedCornerShape(8.dp))
                                                    .border(
                                                        1.dp,
                                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                                        RoundedCornerShape(8.dp)
                                                    )
                                            )
                                            
                                            Spacer(modifier = Modifier.height(4.dp))
                                            
                                            Text(
                                                text = "#${color.toArgb().toUInt().toString(16).uppercase().takeLast(6)}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                // Color harmony info
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = when (index) {
                                            0 -> "Analogous harmony • Cool tones"
                                            1 -> "Complementary harmony • Warm tones"
                                            else -> "Monochromatic harmony • Natural tones"
                                        },
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DesignTemplatesPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Design Templates",
            description = "Ready-to-use design templates"
        ) {
            var selectedCategory by remember { mutableStateOf(TemplateCategory.SOCIAL_MEDIA) }
            
            val templates = remember {
                mapOf(
                    TemplateCategory.SOCIAL_MEDIA to listOf(
                        DesignTemplate("Instagram Post", "Square format, trendy design", Color(0xFFE91E63)),
                        DesignTemplate("Story Template", "Vertical format, engaging layout", Color(0xFF9C27B0)),
                        DesignTemplate("Facebook Cover", "Wide format, professional look", Color(0xFF3F51B5))
                    ),
                    TemplateCategory.BUSINESS to listOf(
                        DesignTemplate("Business Card", "Professional networking", Color(0xFF2196F3)),
                        DesignTemplate("Letterhead", "Corporate identity", Color(0xFF00BCD4)),
                        DesignTemplate("Presentation", "Slide deck template", Color(0xFF009688))
                    ),
                    TemplateCategory.MARKETING to listOf(
                        DesignTemplate("Flyer Design", "Eye-catching promotional", Color(0xFF4CAF50)),
                        DesignTemplate("Banner Ad", "Digital advertising", Color(0xFF8BC34A)),
                        DesignTemplate("Brochure", "Tri-fold marketing material", Color(0xFFCDDC39))
                    )
                )
            }
            
            InteractiveDemo(
                title = "Design Templates",
                description = "Browse and customize design templates",
                controls = {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(TemplateCategory.values()) { category ->
                            FilterChip(
                                onClick = { selectedCategory = category },
                                label = { Text(category.displayName) },
                                selected = selectedCategory == category
                            )
                        }
                    }
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    templates[selectedCategory]?.let { templateList ->
                        items(templateList) { template ->
                            TemplateCard(template)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TypographyStudioPreview() {
    BaseTheme {
        ComponentShowcase(
            title = "Typography Studio",
            description = "Font pairing and text styling tools"
        ) {
            var selectedFont by remember { mutableStateOf("Roboto") }
            var fontSize by remember { mutableStateOf(16f) }
            var lineHeight by remember { mutableStateOf(1.5f) }
            var letterSpacing by remember { mutableStateOf(0f) }
            var sampleText by remember { mutableStateOf("The quick brown fox jumps over the lazy dog") }
            
            val fonts = listOf("Roboto", "Open Sans", "Lato", "Montserrat", "Source Sans Pro")
            
            InteractiveDemo(
                title = "Typography Studio",
                description = "Font pairing and text styling tools",
                controls = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Font Family:",
                            style = MaterialTheme.typography.labelMedium
                        )
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(fonts) { font ->
                                FilterChip(
                                    onClick = { selectedFont = font },
                                    label = { Text(font) },
                                    selected = selectedFont == font
                                )
                            }
                        }
                        
                        Text(
                            text = "Font Size: ${fontSize.toInt()}sp",
                            style = MaterialTheme.typography.labelMedium
                        )
                        
                        Slider(
                            value = fontSize,
                            onValueChange = { fontSize = it },
                            valueRange = 12f..32f,
                            modifier = Modifier.width(200.dp)
                        )
                    }
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Text preview
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Preview",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            OutlinedTextField(
                                value = sampleText,
                                onValueChange = { sampleText = it },
                                label = { Text("Sample Text") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Preview text with applied styling
                            Text(
                                text = sampleText,
                                fontSize = with(LocalDensity.current) { fontSize.toDp().toSp() },
                                lineHeight = with(LocalDensity.current) { (fontSize * lineHeight).toDp().toSp() },
                                letterSpacing = with(LocalDensity.current) { letterSpacing.toDp().toSp() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(16.dp)
                            )
                        }
                    }
                    
                    // Typography controls
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Typography Controls",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            // Line height control
                            Column {
                                Text(
                                    text = "Line Height: ${String.format("%.1f", lineHeight)}",
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Slider(
                                    value = lineHeight,
                                    onValueChange = { lineHeight = it },
                                    valueRange = 1.0f..2.0f
                                )
                            }
                            
                            // Letter spacing control
                            Column {
                                Text(
                                    text = "Letter Spacing: ${String.format("%.1f", letterSpacing)}sp",
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Slider(
                                    value = letterSpacing,
                                    onValueChange = { letterSpacing = it },
                                    valueRange = -2f..4f
                                )
                            }
                        }
                    }
                    
                    // Font pairing suggestions
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Suggested Pairings",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            val pairings = when (selectedFont) {
                                "Roboto" -> listOf("Roboto Slab", "Open Sans")
                                "Open Sans" -> listOf("Merriweather", "Lato")
                                "Montserrat" -> listOf("Source Serif Pro", "Roboto")
                                else -> listOf("Georgia", "Arial")
                            }
                            
                            pairings.forEach { pairing ->
                                Text(
                                    text = "• $selectedFont + $pairing",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Data classes and enums
enum class EditingTool(val displayName: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    FILTERS("Filters", Icons.Default.FilterVintage),
    ADJUSTMENTS("Adjust", Icons.Default.Tune),
    CROP("Crop", Icons.Default.Crop),
    LAYERS("Layers", Icons.Default.Layers)
}

enum class BrushType(val displayName: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    PEN("Pen", Icons.Default.Edit),
    BRUSH("Brush", Icons.Default.Brush),
    PENCIL("Pencil", Icons.Default.Create),
    ERASER("Eraser", Icons.Default.Clear)
}

enum class PaletteType(val displayName: String) {
    COMPLEMENTARY("Complementary"),
    ANALOGOUS("Analogous"),
    TRIADIC("Triadic"),
    MONOCHROMATIC("Monochromatic")
}

enum class TemplateCategory(val displayName: String) {
    SOCIAL_MEDIA("Social Media"),
    BUSINESS("Business"),
    MARKETING("Marketing")
}

data class DrawPath(
    val path: Path,
    val color: Color,
    val strokeWidth: Float,
    val brushType: BrushType
)

data class ColorPalette(
    val name: String,
    val colors: List<Color>
)

data class DesignTemplate(
    val name: String,
    val description: String,
    val accentColor: Color
)

// Helper Composables
@Composable
fun FilterControls(
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Filters",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                FilterChip(
                    onClick = { onFilterSelected(filter) },
                    label = { Text(filter) },
                    selected = selectedFilter == filter
                )
            }
        }
    }
}

@Composable
fun AdjustmentControls(
    brightness: Float,
    contrast: Float,
    saturation: Float,
    onBrightnessChange: (Float) -> Unit,
    onContrastChange: (Float) -> Unit,
    onSaturationChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Adjustments",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        
        // Brightness
        Column {
            Text(
                text = "Brightness: ${(brightness * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium
            )
            Slider(
                value = brightness,
                onValueChange = onBrightnessChange,
                valueRange = -1f..1f
            )
        }
        
        // Contrast
        Column {
            Text(
                text = "Contrast: ${(contrast * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium
            )
            Slider(
                value = contrast,
                onValueChange = onContrastChange,
                valueRange = -1f..1f
            )
        }
        
        // Saturation
        Column {
            Text(
                text = "Saturation: ${(saturation * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium
            )
            Slider(
                value = saturation,
                onValueChange = onSaturationChange,
                valueRange = -1f..1f
            )
        }
    }
}

@Composable
fun CropControls() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Crop & Resize",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        val aspectRatios = listOf("Free", "1:1", "4:3", "16:9", "3:2")
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(aspectRatios) { ratio ->
                FilterChip(
                    onClick = { },
                    label = { Text(ratio) },
                    selected = ratio == "Free"
                )
            }
        }
    }
}

@Composable
fun LayerControls() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Layers",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        val layers = listOf("Background", "Main Image", "Text Overlay", "Effects")
        
        layers.forEach { layer ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = layer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Row {
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = "Toggle Visibility",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Layer Options",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TemplateCard(template: DesignTemplate) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Template preview
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                template.accentColor,
                                template.accentColor.copy(alpha = 0.7f)
                            )
                        ),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Template",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = template.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = template.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Button(
                onClick = { }
            ) {
                Text("Use")
            }
        }
    }
}

// Extension function for drawing paths
fun DrawScope.drawPath(drawPath: DrawPath) {
    val paint = Paint().apply {
        color = drawPath.color
        strokeWidth = drawPath.strokeWidth
        style = PaintingStyle.Stroke
        strokeCap = StrokeCap.Round
        strokeJoin = StrokeJoin.Round
    }
    
    drawPath(
        path = drawPath.path,
        color = drawPath.color,
        style = Stroke(
            width = drawPath.strokeWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}