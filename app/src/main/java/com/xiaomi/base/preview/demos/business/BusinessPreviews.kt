package com.xiaomi.base.preview.demos.business

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.preview.catalog.PreviewCategory
import com.xiaomi.base.preview.catalog.PreviewDifficulty
import com.xiaomi.base.preview.catalog.PreviewItem
import com.xiaomi.base.preview.catalog.PreviewRegistry
import com.xiaomi.base.ui.theme.BaseAppTheme

// Data classes for Business demos
data class SalesMetric(
    val title: String,
    val value: String,
    val change: String,
    val isPositive: Boolean,
    val icon: ImageVector
)

data class TeamMember(
    val name: String,
    val role: String,
    val status: String,
    val avatar: Color,
    val isOnline: Boolean
)

data class ProjectTask(
    val title: String,
    val assignee: String,
    val priority: TaskPriority,
    val dueDate: String,
    val progress: Float
)

enum class TaskPriority(val displayName: String, val color: Color) {
    LOW("Low", Color(0xFF4CAF50)),
    MEDIUM("Medium", Color(0xFFFF9800)),
    HIGH("High", Color(0xFFF44336)),
    URGENT("Urgent", Color(0xFF9C27B0))
}

data class InventoryItem(
    val name: String,
    val sku: String,
    val stock: Int,
    val price: String,
    val status: InventoryStatus
)

enum class InventoryStatus(val displayName: String, val color: Color) {
    IN_STOCK("In Stock", Color(0xFF4CAF50)),
    LOW_STOCK("Low Stock", Color(0xFFFF9800)),
    OUT_OF_STOCK("Out of Stock", Color(0xFFF44336)),
    DISCONTINUED("Discontinued", Color(0xFF9E9E9E))
}

data class CustomerTicket(
    val id: String,
    val customer: String,
    val subject: String,
    val priority: TicketPriority,
    val status: TicketStatus,
    val assignedTo: String,
    val createdAt: String
)

enum class TicketPriority(val displayName: String, val color: Color) {
    LOW("Low", Color(0xFF4CAF50)),
    NORMAL("Normal", Color(0xFF2196F3)),
    HIGH("High", Color(0xFFFF9800)),
    CRITICAL("Critical", Color(0xFFF44336))
}

enum class TicketStatus(val displayName: String, val color: Color) {
    OPEN("Open", Color(0xFF2196F3)),
    IN_PROGRESS("In Progress", Color(0xFFFF9800)),
    RESOLVED("Resolved", Color(0xFF4CAF50)),
    CLOSED("Closed", Color(0xFF9E9E9E))
}

// Business Dashboard Preview
@Preview(name = "Business Dashboard", showBackground = true)
@Composable
fun BusinessDashboardPreview() {
    BaseAppTheme {
        BusinessDashboard()
    }
}

@Composable
fun BusinessDashboard() {
    val salesMetrics = listOf(
        SalesMetric("Revenue", "$125,430", "+12.5%", true, Icons.Default.TrendingUp),
        SalesMetric("Orders", "1,247", "+8.2%", true, Icons.Default.ShoppingCart),
        SalesMetric("Customers", "892", "+15.3%", true, Icons.Default.People),
        SalesMetric("Conversion", "3.2%", "-2.1%", false, Icons.Default.Analytics)
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Business Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(salesMetrics) { metric ->
                    SalesMetricCard(metric)
                }
            }
        }
        
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Sales Performance",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Chart placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = "Chart",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            Text(
                                text = "Sales Chart",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Quick Actions",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        QuickActionButton(
                            icon = Icons.Default.Add,
                            label = "New Order",
                            onClick = { }
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        QuickActionButton(
                            icon = Icons.Default.Person,
                            label = "Add Customer",
                            onClick = { }
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        QuickActionButton(
                            icon = Icons.Default.Inventory,
                            label = "Manage Inventory",
                            onClick = { }
                        )
                    }
                }
                
                Card(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Recent Activity",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        ActivityItem(
                            title = "New order #1247",
                            time = "2 min ago",
                            icon = Icons.Default.ShoppingCart
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        ActivityItem(
                            title = "Customer registered",
                            time = "5 min ago",
                            icon = Icons.Default.PersonAdd
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        ActivityItem(
                            title = "Payment received",
                            time = "10 min ago",
                            icon = Icons.Default.Payment
                        )
                    }
                }
            }
        }
    }
}

// Team Management Preview
@Preview(name = "Team Management", showBackground = true)
@Composable
fun TeamManagementPreview() {
    BaseAppTheme {
        TeamManagement()
    }
}

@Composable
fun TeamManagement() {
    val teamMembers = listOf(
        TeamMember("Alice Johnson", "Product Manager", "Available", Color(0xFF2196F3), true),
        TeamMember("Bob Smith", "Developer", "In Meeting", Color(0xFF4CAF50), true),
        TeamMember("Carol Davis", "Designer", "Away", Color(0xFFFF9800), false),
        TeamMember("David Wilson", "QA Engineer", "Available", Color(0xFF9C27B0), true),
        TeamMember("Eva Brown", "Marketing", "Offline", Color(0xFFF44336), false)
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Team Management",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "Add Member"
                    )
                }
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TeamStatsCard(
                    title = "Total Members",
                    value = "${teamMembers.size}",
                    icon = Icons.Default.People,
                    color = MaterialTheme.colorScheme.primary
                )
                
                TeamStatsCard(
                    title = "Online",
                    value = "${teamMembers.count { it.isOnline }}",
                    icon = Icons.Default.Circle,
                    color = Color(0xFF4CAF50)
                )
                
                TeamStatsCard(
                    title = "Away",
                    value = "${teamMembers.count { !it.isOnline }}",
                    icon = Icons.Default.Circle,
                    color = Color(0xFFFF9800)
                )
            }
        }
        
        items(teamMembers) { member ->
            TeamMemberCard(member)
        }
    }
}

// Project Management Preview
@Preview(name = "Project Management", showBackground = true)
@Composable
fun ProjectManagementPreview() {
    BaseAppTheme {
        ProjectManagement()
    }
}

@Composable
fun ProjectManagement() {
    val tasks = listOf(
        ProjectTask("Design new landing page", "Carol Davis", TaskPriority.HIGH, "Dec 15", 0.75f),
        ProjectTask("Implement user authentication", "Bob Smith", TaskPriority.URGENT, "Dec 12", 0.90f),
        ProjectTask("Write API documentation", "Alice Johnson", TaskPriority.MEDIUM, "Dec 20", 0.30f),
        ProjectTask("Test mobile app", "David Wilson", TaskPriority.HIGH, "Dec 18", 0.60f),
        ProjectTask("Create marketing materials", "Eva Brown", TaskPriority.LOW, "Dec 25", 0.10f)
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Project Tasks",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Task"
                    )
                }
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TaskPriority.values().forEach { priority ->
                    FilterChip(
                        onClick = { },
                        label = {
                            Text(
                                text = priority.displayName,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        selected = false,
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(priority.color, CircleShape)
                            )
                        }
                    )
                }
            }
        }
        
        items(tasks) { task ->
            ProjectTaskCard(task)
        }
    }
}

// Inventory Management Preview
@Preview(name = "Inventory Management", showBackground = true)
@Composable
fun InventoryManagementPreview() {
    BaseAppTheme {
        InventoryManagement()
    }
}

@Composable
fun InventoryManagement() {
    val inventoryItems = listOf(
        InventoryItem("iPhone 15 Pro", "IPH15P-256", 45, "$999", InventoryStatus.IN_STOCK),
        InventoryItem("MacBook Air M2", "MBA-M2-512", 8, "$1,199", InventoryStatus.LOW_STOCK),
        InventoryItem("AirPods Pro", "APP-GEN2", 0, "$249", InventoryStatus.OUT_OF_STOCK),
        InventoryItem("iPad Pro 12.9", "IPD-PRO-1TB", 23, "$1,099", InventoryStatus.IN_STOCK),
        InventoryItem("Apple Watch SE", "AWS-SE-44", 0, "$249", InventoryStatus.DISCONTINUED)
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Inventory Management",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Item"
                    )
                }
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InventoryStatsCard(
                    title = "Total Items",
                    value = inventoryItems.size.toString(),
                    color = MaterialTheme.colorScheme.primary
                )
                
                InventoryStatsCard(
                    title = "Low Stock",
                    value = inventoryItems.count { it.status == InventoryStatus.LOW_STOCK }.toString(),
                    color = Color(0xFFFF9800)
                )
                
                InventoryStatsCard(
                    title = "Out of Stock",
                    value = inventoryItems.count { it.status == InventoryStatus.OUT_OF_STOCK }.toString(),
                    color = Color(0xFFF44336)
                )
            }
        }
        
        items(inventoryItems) { item ->
            InventoryItemCard(item)
        }
    }
}

// Customer Support Preview
@Preview(name = "Customer Support", showBackground = true)
@Composable
fun CustomerSupportPreview() {
    BaseAppTheme {
        CustomerSupport()
    }
}

@Composable
fun CustomerSupport() {
    val tickets = listOf(
        CustomerTicket("#1001", "John Doe", "Login issues", TicketPriority.HIGH, TicketStatus.OPEN, "Alice", "2 hours ago"),
        CustomerTicket("#1002", "Jane Smith", "Payment failed", TicketPriority.CRITICAL, TicketStatus.IN_PROGRESS, "Bob", "4 hours ago"),
        CustomerTicket("#1003", "Mike Johnson", "Feature request", TicketPriority.LOW, TicketStatus.OPEN, "Carol", "1 day ago"),
        CustomerTicket("#1004", "Sarah Wilson", "Bug report", TicketPriority.NORMAL, TicketStatus.RESOLVED, "David", "2 days ago"),
        CustomerTicket("#1005", "Tom Brown", "Account deletion", TicketPriority.HIGH, TicketStatus.CLOSED, "Eva", "3 days ago")
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Customer Support",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "New Ticket"
                    )
                }
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SupportStatsCard(
                    title = "Open",
                    value = tickets.count { it.status == TicketStatus.OPEN }.toString(),
                    color = Color(0xFF2196F3)
                )
                
                SupportStatsCard(
                    title = "In Progress",
                    value = tickets.count { it.status == TicketStatus.IN_PROGRESS }.toString(),
                    color = Color(0xFFFF9800)
                )
                
                SupportStatsCard(
                    title = "Resolved",
                    value = tickets.count { it.status == TicketStatus.RESOLVED }.toString(),
                    color = Color(0xFF4CAF50)
                )
            }
        }
        
        items(tickets) { ticket ->
            CustomerTicketCard(ticket)
        }
    }
}

// Helper Composables
@Composable
fun SalesMetricCard(metric: SalesMetric) {
    Card(
        modifier = Modifier.width(160.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = metric.icon,
                    contentDescription = metric.title,
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = metric.change,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (metric.isPositive) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = metric.value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = metric.title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ActivityItem(
    title: String,
    time: String,
    icon: ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall
            )
            
            Text(
                text = time,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TeamStatsCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
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
fun TeamMemberCard(member: TeamMember) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Surface(
                    shape = CircleShape,
                    color = member.avatar,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = member.name.take(2).uppercase(),
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                if (member.isOnline) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.BottomEnd)
                            .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    ) {}
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = member.role,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (member.isOnline) {
                    Color(0xFF4CAF50).copy(alpha = 0.2f)
                } else {
                    Color(0xFFFF9800).copy(alpha = 0.2f)
                }
            ) {
                Text(
                    text = member.status,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (member.isOnline) Color(0xFF4CAF50) else Color(0xFFFF9800),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ProjectTaskCard(task: ProjectTask) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "Assigned to ${task.assignee}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = task.priority.color.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = task.priority.displayName,
                        style = MaterialTheme.typography.labelSmall,
                        color = task.priority.color,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Progress",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    LinearProgressIndicator(
                        progress = task.progress,
                        modifier = Modifier.width(120.dp)
                    )
                }
                
                Text(
                    text = "Due ${task.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun InventoryStatsCard(
    title: String,
    value: String,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
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
fun InventoryItemCard(item: InventoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = "SKU: ${item.sku}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Stock: ${item.stock}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = item.status.color.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = item.status.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            color = item.status.color,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            
            Text(
                text = item.price,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun SupportStatsCard(
    title: String,
    value: String,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
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
fun CustomerTicketCard(ticket: CustomerTicket) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ticket.id,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ticket.priority.color.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = ticket.priority.displayName,
                                style = MaterialTheme.typography.labelSmall,
                                color = ticket.priority.color,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = ticket.subject,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "Customer: ${ticket.customer}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ticket.status.color.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = ticket.status.displayName,
                        style = MaterialTheme.typography.labelSmall,
                        color = ticket.status.color,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Assigned to ${ticket.assignedTo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = ticket.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Register previews with PreviewRegistry
fun registerBusinessPreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "business_dashboard",
            title = "Business Dashboard",
            description = "Comprehensive business analytics and metrics dashboard with sales performance, quick actions, and activity feed",
            category = PreviewCategory.BUSINESS,
            icon = Icons.Default.Dashboard,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "25 minutes",
            tags = listOf("dashboard", "analytics", "metrics", "sales", "business"),
            content = { BusinessDashboardPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "team_management",
            title = "Team Management",
            description = "Team member management interface with status tracking, online presence, and team statistics",
            category = PreviewCategory.BUSINESS,
            icon = Icons.Default.Group,
            difficulty = PreviewDifficulty.BEGINNER,
            estimatedTime = "20 minutes",
            tags = listOf("team", "management", "members", "status", "collaboration"),
            content = { TeamManagementPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "project_management",
            title = "Project Management",
            description = "Project task management with priority levels, progress tracking, and team assignments",
            category = PreviewCategory.BUSINESS,
            icon = Icons.Default.Assignment,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "30 minutes",
            tags = listOf("project", "tasks", "management", "priority", "progress"),
            content = { ProjectManagementPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "inventory_management",
            title = "Inventory Management",
            description = "Inventory tracking system with stock levels, status indicators, and product management",
            category = PreviewCategory.BUSINESS,
            icon = Icons.Default.Inventory,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            estimatedTime = "25 minutes",
            tags = listOf("inventory", "stock", "products", "management", "tracking"),
            content = { InventoryManagementPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "customer_support",
            title = "Customer Support",
            description = "Customer support ticket system with priority levels, status tracking, and assignment management",
            category = PreviewCategory.BUSINESS,
            icon = Icons.Default.Support,
            difficulty = PreviewDifficulty.ADVANCED,
            estimatedTime = "35 minutes",
            tags = listOf("support", "tickets", "customer", "priority", "status"),
            content = { CustomerSupportPreview() }
        )
    )
}