package com.xiaomi.base.preview.demos.finance

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.preview.catalog.PreviewCategory
import com.xiaomi.base.preview.catalog.PreviewDifficulty
import com.xiaomi.base.preview.catalog.PreviewItem
import com.xiaomi.base.preview.catalog.PreviewRegistry
import com.xiaomi.base.ui.theme.BaseAppTheme
import kotlin.math.abs

// Data classes for Finance demos
data class Account(
    val id: String,
    val name: String,
    val type: AccountType,
    val balance: Double,
    val currency: String = "USD",
    val accountNumber: String,
    val color: Color,
    val isHidden: Boolean = false
)

enum class AccountType {
    CHECKING, SAVINGS, CREDIT_CARD, INVESTMENT, LOAN
}

data class Transaction(
    val id: String,
    val title: String,
    val description: String,
    val amount: Double,
    val date: String,
    val category: TransactionCategory,
    val account: Account,
    val isIncome: Boolean,
    val merchant: String? = null,
    val location: String? = null,
    val isPending: Boolean = false
)

enum class TransactionCategory {
    FOOD, TRANSPORT, SHOPPING, ENTERTAINMENT, BILLS, SALARY, INVESTMENT, HEALTHCARE, EDUCATION, OTHER
}

data class Investment(
    val id: String,
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val change: Double,
    val changePercent: Double,
    val shares: Double,
    val totalValue: Double,
    val color: Color
)

data class Budget(
    val id: String,
    val category: TransactionCategory,
    val name: String,
    val budgetAmount: Double,
    val spentAmount: Double,
    val period: String,
    val color: Color
) {
    val remainingAmount: Double get() = budgetAmount - spentAmount
    val progressPercent: Float get() = (spentAmount / budgetAmount).toFloat().coerceIn(0f, 1f)
}

data class Goal(
    val id: String,
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double,
    val targetDate: String,
    val category: String,
    val color: Color
) {
    val progressPercent: Float get() = (currentAmount / targetAmount).toFloat().coerceIn(0f, 1f)
}

data class CreditCard(
    val id: String,
    val name: String,
    val lastFourDigits: String,
    val balance: Double,
    val creditLimit: Double,
    val dueDate: String,
    val minimumPayment: Double,
    val color: Color
) {
    val availableCredit: Double get() = creditLimit - balance
    val utilizationPercent: Float get() = (balance / creditLimit).toFloat().coerceIn(0f, 1f)
}

// Banking Dashboard Preview
@Preview(name = "Banking Dashboard", showBackground = true)
@Composable
fun BankingDashboardPreview() {
    BaseAppTheme {
        BankingDashboard()
    }
}

@Composable
fun BankingDashboard() {
    val accounts = listOf(
        Account(
            id = "1",
            name = "Main Checking",
            type = AccountType.CHECKING,
            balance = 5420.50,
            accountNumber = "****1234",
            color = Color(0xFF2196F3)
        ),
        Account(
            id = "2",
            name = "Savings Account",
            type = AccountType.SAVINGS,
            balance = 15750.00,
            accountNumber = "****5678",
            color = Color(0xFF4CAF50)
        ),
        Account(
            id = "3",
            name = "Credit Card",
            type = AccountType.CREDIT_CARD,
            balance = -1250.75,
            accountNumber = "****9012",
            color = Color(0xFFFF5722)
        )
    )
    
    val recentTransactions = listOf(
        Transaction(
            id = "1",
            title = "Starbucks Coffee",
            description = "Coffee and pastry",
            amount = -12.50,
            date = "Today",
            category = TransactionCategory.FOOD,
            account = accounts[0],
            isIncome = false,
            merchant = "Starbucks",
            location = "Downtown"
        ),
        Transaction(
            id = "2",
            title = "Salary Deposit",
            description = "Monthly salary",
            amount = 4500.00,
            date = "Yesterday",
            category = TransactionCategory.SALARY,
            account = accounts[0],
            isIncome = true
        ),
        Transaction(
            id = "3",
            title = "Amazon Purchase",
            description = "Electronics",
            amount = -89.99,
            date = "2 days ago",
            category = TransactionCategory.SHOPPING,
            account = accounts[2],
            isIncome = false,
            merchant = "Amazon",
            isPending = true
        )
    )
    
    val quickActions = listOf(
        "Transfer" to Icons.Default.SwapHoriz,
        "Pay Bills" to Icons.Default.Receipt,
        "Deposit" to Icons.Default.Add,
        "ATM" to Icons.Default.LocalAtm
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            BankingHeader()
        }
        
        item {
            AccountsOverview(accounts)
        }
        
        item {
            QuickActionsSection(quickActions)
        }
        
        item {
            RecentTransactionsSection(recentTransactions)
        }
    }
}

// Investment Portfolio Preview
@Preview(name = "Investment Portfolio", showBackground = true)
@Composable
fun InvestmentPortfolioPreview() {
    BaseAppTheme {
        InvestmentPortfolio()
    }
}

@Composable
fun InvestmentPortfolio() {
    val totalValue = 45750.25
    val totalGain = 3250.75
    val totalGainPercent = 7.65
    
    val investments = listOf(
        Investment(
            id = "1",
            symbol = "AAPL",
            name = "Apple Inc.",
            currentPrice = 175.50,
            change = 2.25,
            changePercent = 1.30,
            shares = 50.0,
            totalValue = 8775.00,
            color = Color(0xFF2196F3)
        ),
        Investment(
            id = "2",
            symbol = "GOOGL",
            name = "Alphabet Inc.",
            currentPrice = 2750.80,
            change = -15.20,
            changePercent = -0.55,
            shares = 10.0,
            totalValue = 27508.00,
            color = Color(0xFF4CAF50)
        ),
        Investment(
            id = "3",
            symbol = "TSLA",
            name = "Tesla Inc.",
            currentPrice = 245.30,
            change = 8.75,
            changePercent = 3.70,
            shares = 25.0,
            totalValue = 6132.50,
            color = Color(0xFFE91E63)
        ),
        Investment(
            id = "4",
            symbol = "MSFT",
            name = "Microsoft Corp.",
            currentPrice = 335.20,
            change = -2.10,
            changePercent = -0.62,
            shares = 10.0,
            totalValue = 3352.00,
            color = Color(0xFFFF9800)
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            InvestmentHeader()
        }
        
        item {
            PortfolioSummary(totalValue, totalGain, totalGainPercent)
        }
        
        item {
            PortfolioChart()
        }
        
        item {
            InvestmentsList(investments)
        }
    }
}

// Expense Tracking Preview
@Preview(name = "Expense Tracking", showBackground = true)
@Composable
fun ExpenseTrackingPreview() {
    BaseAppTheme {
        ExpenseTracking()
    }
}

@Composable
fun ExpenseTracking() {
    val monthlySpending = 2850.75
    val monthlyBudget = 3200.00
    val remainingBudget = monthlyBudget - monthlySpending
    
    val budgets = listOf(
        Budget(
            id = "1",
            category = TransactionCategory.FOOD,
            name = "Food & Dining",
            budgetAmount = 800.0,
            spentAmount = 650.25,
            period = "This Month",
            color = Color(0xFFE91E63)
        ),
        Budget(
            id = "2",
            category = TransactionCategory.TRANSPORT,
            name = "Transportation",
            budgetAmount = 400.0,
            spentAmount = 320.50,
            period = "This Month",
            color = Color(0xFF2196F3)
        ),
        Budget(
            id = "3",
            category = TransactionCategory.SHOPPING,
            name = "Shopping",
            budgetAmount = 600.0,
            spentAmount = 750.00,
            period = "This Month",
            color = Color(0xFFFF9800)
        ),
        Budget(
            id = "4",
            category = TransactionCategory.ENTERTAINMENT,
            name = "Entertainment",
            budgetAmount = 300.0,
            spentAmount = 180.75,
            period = "This Month",
            color = Color(0xFF9C27B0)
        )
    )
    
    val categorySpending = mapOf(
        "Food" to 650.25,
        "Transport" to 320.50,
        "Shopping" to 750.00,
        "Entertainment" to 180.75,
        "Bills" to 450.00,
        "Other" to 499.25
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            ExpenseHeader()
        }
        
        item {
            MonthlyOverview(monthlySpending, monthlyBudget, remainingBudget)
        }
        
        item {
            SpendingChart(categorySpending)
        }
        
        item {
            BudgetsList(budgets)
        }
    }
}

// Budget Management Preview
@Preview(name = "Budget Management", showBackground = true)
@Composable
fun BudgetManagementPreview() {
    BaseAppTheme {
        BudgetManagement()
    }
}

@Composable
fun BudgetManagement() {
    val goals = listOf(
        Goal(
            id = "1",
            name = "Emergency Fund",
            targetAmount = 10000.0,
            currentAmount = 6500.0,
            targetDate = "Dec 2024",
            category = "Savings",
            color = Color(0xFF4CAF50)
        ),
        Goal(
            id = "2",
            name = "Vacation to Europe",
            targetAmount = 5000.0,
            currentAmount = 2750.0,
            targetDate = "Jun 2024",
            category = "Travel",
            color = Color(0xFF2196F3)
        ),
        Goal(
            id = "3",
            name = "New Car",
            targetAmount = 25000.0,
            currentAmount = 8500.0,
            targetDate = "Mar 2025",
            category = "Transportation",
            color = Color(0xFFFF9800)
        )
    )
    
    val creditCards = listOf(
        CreditCard(
            id = "1",
            name = "Chase Sapphire",
            lastFourDigits = "1234",
            balance = 1250.75,
            creditLimit = 5000.0,
            dueDate = "Jan 15",
            minimumPayment = 35.0,
            color = Color(0xFF2196F3)
        ),
        CreditCard(
            id = "2",
            name = "Capital One",
            lastFourDigits = "5678",
            balance = 850.25,
            creditLimit = 3000.0,
            dueDate = "Jan 20",
            minimumPayment = 25.0,
            color = Color(0xFFE91E63)
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            BudgetHeader()
        }
        
        item {
            SavingsGoalsSection(goals)
        }
        
        item {
            CreditCardsSection(creditCards)
        }
        
        item {
            BudgetInsights()
        }
    }
}

// Cryptocurrency Wallet Preview
@Preview(name = "Cryptocurrency Wallet", showBackground = true)
@Composable
fun CryptocurrencyWalletPreview() {
    BaseAppTheme {
        CryptocurrencyWallet()
    }
}

@Composable
fun CryptocurrencyWallet() {
    val totalValue = 12750.50
    val totalChange = 850.25
    val totalChangePercent = 7.15
    
    val cryptos = listOf(
        Investment(
            id = "1",
            symbol = "BTC",
            name = "Bitcoin",
            currentPrice = 45250.0,
            change = 1250.0,
            changePercent = 2.84,
            shares = 0.15,
            totalValue = 6787.50,
            color = Color(0xFFFF9800)
        ),
        Investment(
            id = "2",
            symbol = "ETH",
            name = "Ethereum",
            currentPrice = 3150.0,
            change = -85.0,
            changePercent = -2.63,
            shares = 1.25,
            totalValue = 3937.50,
            color = Color(0xFF2196F3)
        ),
        Investment(
            id = "3",
            symbol = "ADA",
            name = "Cardano",
            currentPrice = 0.85,
            change = 0.05,
            changePercent = 6.25,
            shares = 1500.0,
            totalValue = 1275.00,
            color = Color(0xFF4CAF50)
        ),
        Investment(
            id = "4",
            symbol = "DOT",
            name = "Polkadot",
            currentPrice = 25.0,
            change = -1.5,
            changePercent = -5.66,
            shares = 30.0,
            totalValue = 750.00,
            color = Color(0xFFE91E63)
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            CryptoHeader()
        }
        
        item {
            CryptoPortfolioSummary(totalValue, totalChange, totalChangePercent)
        }
        
        item {
            CryptoChart()
        }
        
        item {
            CryptoList(cryptos)
        }
        
        item {
            CryptoActions()
        }
    }
}

@Composable
fun CryptoActions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Buy",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Buy")
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        OutlinedButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Sell",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sell")
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        OutlinedButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = "Swap",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Swap")
        }
    }
}

@Composable
fun CryptoItem(crypto: Investment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = crypto.color,
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = crypto.symbol.take(3),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = crypto.symbol,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = crypto.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = "${String.format("%.6f", crypto.shares)} ${crypto.symbol}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$${String.format("%.2f", crypto.totalValue)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = "$${String.format("%.2f", crypto.currentPrice)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (crypto.change >= 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    contentDescription = if (crypto.change >= 0) "Gain" else "Loss",
                    tint = if (crypto.change >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.size(16.dp)
                )
                
                Spacer(modifier = Modifier.width(2.dp))
                
                Text(
                    text = "${String.format("%.2f", crypto.changePercent)}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (crypto.change >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun CryptoList(cryptos: List<Investment>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Your Crypto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        cryptos.forEach { crypto ->
            CryptoItem(crypto)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CryptoHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Crypto Wallet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Row {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = "QR Code"
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
        }
    }
}

@Composable
fun CryptoPortfolioSummary(totalValue: Double, totalChange: Double, totalChangePercent: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Balance",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "$${String.format("%.2f", totalValue)}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (totalChange >= 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    contentDescription = if (totalChange >= 0) "Gain" else "Loss",
                    tint = if (totalChange >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(4.dp))
                
                Text(
                    text = "${if (totalChange >= 0) "+" else ""}$${String.format("%.2f", totalChange)} (${String.format("%.2f", totalChangePercent)}%)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (totalChange >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun CryptoChart() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Portfolio Performance",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Placeholder for crypto chart
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "₿ Crypto Chart",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Helper Composables
@Composable
fun BankingHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good morning,",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "John Doe",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        Row {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile"
                )
            }
        }
    }
}

@Composable
fun AccountsOverview(accounts: List<Account>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Your Accounts",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(accounts) { account ->
                AccountCard(account)
            }
        }
    }
}

@Composable
fun AccountCard(account: Account) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(160.dp)
            .clickable { },
        colors = CardDefaults.cardColors(
            containerColor = account.color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                
                Icon(
                    imageVector = when (account.type) {
                        AccountType.CHECKING -> Icons.Default.AccountBalance
                        AccountType.SAVINGS -> Icons.Default.Savings
                        AccountType.CREDIT_CARD -> Icons.Default.CreditCard
                        AccountType.INVESTMENT -> Icons.Default.TrendingUp
                        AccountType.LOAN -> Icons.Default.MonetizationOn
                    },
                    contentDescription = account.type.name,
                    tint = Color.White.copy(alpha = 0.8f)
                )
            }
            
            Column {
                Text(
                    text = account.accountNumber,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = if (account.balance >= 0) {
                        "$${String.format("%.2f", account.balance)}"
                    } else {
                        "-$${String.format("%.2f", abs(account.balance))}"
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun QuickActionsSection(actions: List<Pair<String, ImageVector>>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            actions.forEach { (title, icon) ->
                QuickActionItem(title, icon)
            }
        }
    }
}

@Composable
fun QuickActionItem(title: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(56.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RecentTransactionsSection(transactions: List<Transaction>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            TextButton(onClick = { }) {
                Text("See All")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        transactions.forEach { transaction ->
            TransactionItem(transaction)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = getCategoryColor(transaction.category).copy(alpha = 0.1f),
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getCategoryIcon(transaction.category),
                    contentDescription = transaction.category.name,
                    tint = getCategoryColor(transaction.category),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                
                Text(
                    text = if (transaction.amount >= 0) {
                        "+$${String.format("%.2f", transaction.amount)}"
                    } else {
                        "-$${String.format("%.2f", abs(transaction.amount))}"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = if (transaction.amount >= 0) {
                        Color(0xFF4CAF50)
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (transaction.isPending) {
                        Text(
                            text = "Pending",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFFF9800)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    
                    Text(
                        text = transaction.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

fun getCategoryColor(category: TransactionCategory): Color {
    return when (category) {
        TransactionCategory.FOOD -> Color(0xFFE91E63)
        TransactionCategory.TRANSPORT -> Color(0xFF2196F3)
        TransactionCategory.SHOPPING -> Color(0xFFFF9800)
        TransactionCategory.ENTERTAINMENT -> Color(0xFF9C27B0)
        TransactionCategory.BILLS -> Color(0xFFF44336)
        TransactionCategory.SALARY -> Color(0xFF4CAF50)
        TransactionCategory.INVESTMENT -> Color(0xFF607D8B)
        TransactionCategory.HEALTHCARE -> Color(0xFF00BCD4)
        TransactionCategory.EDUCATION -> Color(0xFF3F51B5)
        TransactionCategory.OTHER -> Color(0xFF795548)
    }
}

fun getCategoryIcon(category: TransactionCategory): ImageVector {
    return when (category) {
        TransactionCategory.FOOD -> Icons.Default.Restaurant
        TransactionCategory.TRANSPORT -> Icons.Default.DirectionsCar
        TransactionCategory.SHOPPING -> Icons.Default.ShoppingBag
        TransactionCategory.ENTERTAINMENT -> Icons.Default.Movie
        TransactionCategory.BILLS -> Icons.Default.Receipt
        TransactionCategory.SALARY -> Icons.Default.Work
        TransactionCategory.INVESTMENT -> Icons.Default.TrendingUp
        TransactionCategory.HEALTHCARE -> Icons.Default.LocalHospital
        TransactionCategory.EDUCATION -> Icons.Default.School
        TransactionCategory.OTHER -> Icons.Default.Category
    }
}

@Composable
fun InvestmentHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Portfolio",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Row {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
        }
    }
}

@Composable
fun PortfolioSummary(totalValue: Double, totalGain: Double, totalGainPercent: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Portfolio Value",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "$${String.format("%.2f", totalValue)}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (totalGain >= 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    contentDescription = if (totalGain >= 0) "Gain" else "Loss",
                    tint = if (totalGain >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(4.dp))
                
                Text(
                    text = "${if (totalGain >= 0) "+" else ""}$${String.format("%.2f", totalGain)} (${String.format("%.2f", totalGainPercent)}%)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (totalGain >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun PortfolioChart() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Performance (1Y)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Placeholder for chart
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📈 Portfolio Chart",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun InvestmentsList(investments: List<Investment>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Holdings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        investments.forEach { investment ->
            InvestmentItem(investment)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun InvestmentItem(investment: Investment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = investment.color,
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = investment.symbol.take(2),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = investment.symbol,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = investment.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = "${investment.shares} shares",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$${String.format("%.2f", investment.totalValue)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (investment.change >= 0) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    contentDescription = if (investment.change >= 0) "Gain" else "Loss",
                    tint = if (investment.change >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.size(16.dp)
                )
                
                Spacer(modifier = Modifier.width(2.dp))
                
                Text(
                    text = "${String.format("%.2f", investment.changePercent)}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (investment.change >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun ExpenseHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Expenses",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Row {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter"
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date Range"
                )
            }
        }
    }
}

@Composable
fun MonthlyOverview(monthlySpending: Double, monthlyBudget: Double, remainingBudget: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "This Month",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Spent",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "$${String.format("%.2f", monthlySpending)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column {
                    Text(
                        text = "Budget",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "$${String.format("%.2f", monthlyBudget)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column {
                    Text(
                        text = "Remaining",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "$${String.format("%.2f", remainingBudget)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (remainingBudget >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LinearProgressIndicator(
                progress = (monthlySpending / monthlyBudget).toFloat().coerceIn(0f, 1f),
                modifier = Modifier.fillMaxWidth(),
                color = if (monthlySpending <= monthlyBudget) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color(0xFFF44336)
                }
            )
        }
    }
}

@Composable
fun SpendingChart(categorySpending: Map<String, Double>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Spending by Category",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Placeholder for pie chart
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📊 Spending Chart",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            categorySpending.entries.take(3).forEach { (category, amount) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Text(
                        text = "$${String.format("%.2f", amount)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun BudgetsList(budgets: List<Budget>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Budget Categories",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        budgets.forEach { budget ->
            BudgetItem(budget)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun BudgetItem(budget: Budget) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = budget.color.copy(alpha = 0.1f),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = getCategoryIcon(budget.category),
                                contentDescription = budget.name,
                                tint = budget.color,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            text = budget.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Text(
                            text = budget.period,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$${String.format("%.2f", budget.spentAmount)} / $${String.format("%.2f", budget.budgetAmount)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = if (budget.remainingAmount >= 0) {
                            "$${String.format("%.2f", budget.remainingAmount)} left"
                        } else {
                            "$${String.format("%.2f", abs(budget.remainingAmount))} over"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = if (budget.remainingAmount >= 0) {
                            Color(0xFF4CAF50)
                        } else {
                            Color(0xFFF44336)
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = budget.progressPercent,
                modifier = Modifier.fillMaxWidth(),
                color = if (budget.progressPercent <= 1f) {
                    budget.color
                } else {
                    Color(0xFFF44336)
                }
            )
        }
    }
}

@Composable
fun BudgetHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Budget & Goals",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Goal"
            )
        }
    }
}

@Composable
fun SavingsGoalsSection(goals: List<Goal>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Savings Goals",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        goals.forEach { goal ->
            GoalItem(goal)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun GoalItem(goal: Goal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = goal.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "Target: ${goal.targetDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$${String.format("%.0f", goal.currentAmount)} / $${String.format("%.0f", goal.targetAmount)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "${String.format("%.1f", goal.progressPercent * 100)}% complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = goal.progressPercent,
                modifier = Modifier.fillMaxWidth(),
                color = goal.color
            )
        }
    }
}

@Composable
fun CreditCardsSection(creditCards: List<CreditCard>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Credit Cards",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        creditCards.forEach { card ->
            CreditCardItem(card)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CreditCardItem(card: CreditCard) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = card.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "****${card.lastFourDigits}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$${String.format("%.2f", card.balance)}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "Due ${card.dueDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Available: $${String.format("%.2f", card.availableCredit)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "${String.format("%.1f", card.utilizationPercent * 100)}% used",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (card.utilizationPercent > 0.8f) {
                        Color(0xFFF44336)
                    } else if (card.utilizationPercent > 0.5f) {
                        Color(0xFFFF9800)
                    } else {
                        Color(0xFF4CAF50)
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = card.utilizationPercent,
                modifier = Modifier.fillMaxWidth(),
                color = if (card.utilizationPercent > 0.8f) {
                    Color(0xFFF44336)
                } else if (card.utilizationPercent > 0.5f) {
                    Color(0xFFFF9800)
                } else {
                    Color(0xFF4CAF50)
                }
            )
        }
    }
}

@Composable
fun BudgetInsights() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "💡 Insights",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "• You're spending 25% more on shopping this month",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            
            Text(
                text = "• Great job staying under budget for food!",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            
            Text(
                text = "• Consider setting up automatic savings",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}









// Register previews with PreviewRegistry
fun registerFinancePreviews() {
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "finance_banking_dashboard",
            title = "Banking Dashboard",
            description = "Complete banking dashboard with accounts overview, quick actions, and recent transactions",
            category = PreviewCategory.FINANCE,
            icon = Icons.Default.AccountBalance,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("banking", "dashboard", "accounts", "transactions"),
            estimatedTime = "25 min",
            content = { BankingDashboardPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "finance_investment_portfolio",
            title = "Investment Portfolio",
            description = "Investment portfolio tracker with performance charts and holdings overview",
            category = PreviewCategory.FINANCE,
            icon = Icons.Default.TrendingUp,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("investment", "portfolio", "stocks", "charts"),
            estimatedTime = "35 min",
            content = { InvestmentPortfolioPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "finance_expense_tracking",
            title = "Expense Tracking",
            description = "Expense tracking interface with budget monitoring and spending analysis",
            category = PreviewCategory.FINANCE,
            icon = Icons.Default.Receipt,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("expenses", "budget", "tracking", "analytics"),
            estimatedTime = "20 min",
            content = { ExpenseTrackingPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "finance_budget_management",
            title = "Budget Management",
            description = "Budget management with savings goals, credit cards, and financial insights",
            category = PreviewCategory.FINANCE,
            icon = Icons.Default.Savings,
            difficulty = PreviewDifficulty.INTERMEDIATE,
            tags = listOf("budget", "goals", "credit cards", "savings"),
            estimatedTime = "30 min",
            content = { BudgetManagementPreview() }
        )
    )
    
    PreviewRegistry.registerPreview(
        PreviewItem(
            id = "finance_cryptocurrency_wallet",
            title = "Cryptocurrency Wallet",
            description = "Cryptocurrency wallet with portfolio tracking and trading actions",
            category = PreviewCategory.FINANCE,
            icon = Icons.Default.CurrencyBitcoin,
            difficulty = PreviewDifficulty.ADVANCED,
            tags = listOf("crypto", "wallet", "bitcoin", "trading"),
            estimatedTime = "40 min",
            content = { CryptocurrencyWalletPreview() }
        )
    )
}