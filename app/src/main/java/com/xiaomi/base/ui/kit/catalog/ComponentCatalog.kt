package com.xiaomi.base.ui.kit.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.components.actions.buttons.XiaomiPrimaryButton
import com.xiaomi.base.ui.kit.components.actions.buttons.XiaomiSecondaryButton
import com.xiaomi.base.ui.kit.components.actions.buttons.XiaomiTertiaryButton
import com.xiaomi.base.ui.kit.components.containment.cards.XiaomiCard
import com.xiaomi.base.ui.kit.components.containment.cards.XiaomiElevatedCard
import com.xiaomi.base.ui.kit.components.containment.cards.XiaomiOutlinedCard
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.spacing

/**
 * Data class representing a component category in the catalog
 */
data class ComponentCategory(
    val title: String,
    val description: String,
    val components: List<ComponentItem>
)

/**
 * Data class representing an individual component item
 */
data class ComponentItem(
    val name: String,
    val description: String,
    val content: @Composable () -> Unit
)

/**
 * Xiaomi Base UI Kit Component Catalog
 * 
 * A comprehensive showcase of all available components in the UI Kit.
 * This serves as both documentation and testing ground for components.
 */
@Composable
fun ComponentCatalog(
    modifier: Modifier = Modifier
) {
    val categories = getComponentCategories()
    
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.spacing.ScreenPaddingHorizontal),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.ScreenContentSpacing)
    ) {
        item {
            Column {
                Text(
                    text = "Xiaomi Base UI Kit",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Component Catalog & Design System",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = MaterialTheme.spacing.Small)
                )
            }
        }
        
        items(categories) { category ->
            ComponentCategorySection(
                category = category,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Component category section displaying a group of related components
 */
@Composable
fun ComponentCategorySection(
    category: ComponentCategory,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.Large)
    ) {
        // Category header
        Column {
            Text(
                text = category.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = category.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.spacing.ExtraSmall)
            )
        }
        
        // Components in this category
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.XXL)
        ) {
            category.components.forEach { component ->
                ComponentShowcase(
                    component = component,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Individual component showcase with name, description, and preview
 */
@Composable
fun ComponentShowcase(
    component: ComponentItem,
    modifier: Modifier = Modifier
) {
    XiaomiCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.Large),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.Medium)
        ) {
            // Component info
            Column {
                Text(
                    text = component.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = component.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Component preview
            component.content()
        }
    }
}

/**
 * Get all component categories for the catalog
 */
fun getComponentCategories(): List<ComponentCategory> {
    return listOf(
        ComponentCategory(
            title = "Actions",
            description = "Interactive components that trigger actions",
            components = getActionComponents()
        ),
        ComponentCategory(
            title = "Containment",
            description = "Components that group and organize content",
            components = getContainmentComponents()
        ),
        ComponentCategory(
            title = "Foundation",
            description = "Design tokens and basic styling elements",
            components = getFoundationComponents()
        )
    )
}

/**
 * Get action components (buttons, FABs, etc.)
 */
fun getActionComponents(): List<ComponentItem> {
    return listOf(
        ComponentItem(
            name = "Xiaomi Button",
            description = "Primary action button with Xiaomi styling"
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                XiaomiPrimaryButton(
                    onClick = { }
                ) {
                    Text("Filled Button")
                }
                XiaomiSecondaryButton(
                    onClick = { }
                ) {
                    Text("Tonal Button")
                }
                XiaomiTertiaryButton(
                    onClick = { }
                ) {
                    Text("Outlined Button")
                }
            }
        }
    )
}

/**
 * Get containment components (cards, lists, etc.)
 */
fun getContainmentComponents(): List<ComponentItem> {
    return listOf(
        ComponentItem(
            name = "Xiaomi Card",
            description = "Flexible container for grouping related content"
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                XiaomiCard {
                    Text(
                        text = "Basic Card",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                XiaomiElevatedCard {
                    Text(
                        text = "Elevated Card",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                XiaomiOutlinedCard {
                    Text(
                        text = "Outlined Card",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        ComponentItem(
            name = "Specialized Cards",
            description = "Cards with Xiaomi-specific styling and use cases"
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // XiaomiProductCard { // This component is no longer in the kit
                //     Text(
                //         text = "Product Card",
                //         modifier = Modifier.padding(MaterialTheme.spacing.Medium),
                //         style = MaterialTheme.typography.titleSmall
                //     )
                // }
                // XiaomiFeatureCard { // This component is no longer in the kit
                //     Text(
                //         text = "Feature Card",
                //         modifier = Modifier.padding(MaterialTheme.spacing.Medium),
                //         style = MaterialTheme.typography.titleSmall,
                //         color = MaterialTheme.colorScheme.onPrimaryContainer
                //     )
                // }
            }
        }
    )
}

/**
 * Get foundation components (colors, typography, spacing)
 */
fun getFoundationComponents(): List<ComponentItem> {
    return listOf(
        ComponentItem(
            name = "Typography Scale",
            description = "Material Design 3 typography with Xiaomi fonts"
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Display Large",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "Headline Medium",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Title Large",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Body Large",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Label Medium",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        ComponentItem(
            name = "Color Palette",
            description = "Xiaomi brand colors and Material Design 3 tokens"
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Primary",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Secondary",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Tertiary",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Success", 
                    color = Color(0xFF4CAF50), // Green color for success
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Warning",
                    color = Color(0xFFFF9800), // Orange color for warning
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    )
}

@Preview(name = "Component Catalog - Light")
@Composable
fun ComponentCatalogPreview() {
    XiaomiPreviewTheme {
        ComponentCatalog()
    }
}

@Preview(name = "Component Catalog - Dark")
@Composable
fun ComponentCatalogDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        ComponentCatalog()
    }
}