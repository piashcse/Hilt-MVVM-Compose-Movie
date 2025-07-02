# Review Gate V2 - Windows PowerShell Uninstallation Script
# Author: Lakshman Turlapati
# This script removes Review Gate V2 from Cursor IDE on Windows

# Enable strict error handling
$ErrorActionPreference = "Stop"

# Function to write colored output
function Write-ColorOutput {
    param(
        [string]$Message,
        [string]$Color = "White"
    )
    Write-Host $Message -ForegroundColor $Color
}

Write-ColorOutput "🗑️ Review Gate V2 - Windows Uninstallation" "Cyan"
Write-ColorOutput "==========================================" "Cyan"
Write-Host ""

# Define paths
$CursorExtensionsDir = Join-Path $env:USERPROFILE "cursor-extensions"
$ReviewGateDir = Join-Path $CursorExtensionsDir "review-gate-v2"
$CursorMcpFile = Join-Path $env:USERPROFILE ".cursor\mcp.json"
$CursorRulesDir = Join-Path $env:APPDATA "Cursor\User\rules"

# Remove MCP server directory
if (Test-Path $ReviewGateDir) {
    Write-ColorOutput "🗂️ Removing Review Gate installation directory..." "Yellow"
    try {
        Remove-Item $ReviewGateDir -Recurse -Force
        Write-ColorOutput "✅ Installation directory removed" "Green"
    } catch {
        Write-ColorOutput "❌ Failed to remove installation directory" "Red"
        Write-ColorOutput "💡 Please remove manually: $ReviewGateDir" "Yellow"
    }
} else {
    Write-ColorOutput "ℹ️ Installation directory not found" "Gray"
}

# Remove from MCP configuration
if (Test-Path $CursorMcpFile) {
    Write-ColorOutput "⚙️ Removing from MCP configuration..." "Yellow"
    
    # Backup current config
    $timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
    $BackupFile = "$CursorMcpFile.backup_before_uninstall.$timestamp"
    Copy-Item $CursorMcpFile $BackupFile -Force
    Write-ColorOutput "💾 Backup created: $BackupFile" "Gray"
    
    try {
        $config = Get-Content $CursorMcpFile -Raw | ConvertFrom-Json
        if ($config.mcpServers -and $config.mcpServers.PSObject.Properties.Name -contains "review-gate-v2") {
            $config.mcpServers.PSObject.Properties.Remove("review-gate-v2")
            
            # If no servers left, create empty config
            if ($config.mcpServers.PSObject.Properties.Name.Count -eq 0) {
                $config.mcpServers = @{}
            }
            
            $config | ConvertTo-Json -Depth 10 | Set-Content $CursorMcpFile -Encoding UTF8
            Write-ColorOutput "✅ Removed from MCP configuration" "Green"
            
            $remainingCount = $config.mcpServers.PSObject.Properties.Name.Count
            Write-ColorOutput "Remaining MCP servers: $remainingCount" "Cyan"
        } else {
            Write-ColorOutput "ℹ️ Review Gate not found in MCP configuration" "Gray"
        }
    } catch {
        Write-ColorOutput "❌ Failed to update MCP configuration" "Red"
        Write-ColorOutput "💡 Please remove manually or restore from backup" "Yellow"
    }
} else {
    Write-ColorOutput "ℹ️ MCP configuration file not found" "Gray"
}

# Remove global rule
$ruleFile = Join-Path $CursorRulesDir "ReviewGate.mdc"
if (Test-Path $ruleFile) {
    Write-ColorOutput "📜 Removing global rule..." "Yellow"
    try {
        Remove-Item $ruleFile -Force
        Write-ColorOutput "✅ Global rule removed" "Green"
    } catch {
        Write-ColorOutput "❌ Failed to remove global rule" "Red"
        Write-ColorOutput "💡 Please remove manually: $ruleFile" "Yellow"
    }
} else {
    Write-ColorOutput "ℹ️ Global rule not found" "Gray"
}

# Clean up temporary files
Write-ColorOutput "🧹 Cleaning up temporary files..." "Yellow"
Get-ChildItem $env:TEMP -Filter "review_gate_*" -ErrorAction SilentlyContinue | Remove-Item -Force -ErrorAction SilentlyContinue
Get-ChildItem $env:TEMP -Filter "mcp_response*" -ErrorAction SilentlyContinue | Remove-Item -Force -ErrorAction SilentlyContinue
Write-ColorOutput "✅ Temporary files cleaned" "Green"

Write-Host ""
Write-ColorOutput "📋 MANUAL STEPS REQUIRED:" "Cyan"
Write-ColorOutput "1. Open Cursor IDE" "White"
Write-ColorOutput "2. Press Ctrl+Shift+X to open Extensions" "White"
Write-ColorOutput "3. Search for 'Review Gate' and uninstall the extension" "White"
Write-ColorOutput "4. Restart Cursor when prompted" "White"
Write-Host ""

Write-ColorOutput "🎉 Review Gate V2 Uninstallation Complete!" "Green"
Write-ColorOutput "=========================================" "Green"
Write-Host ""
Write-ColorOutput "📍 What was removed:" "Cyan"
Write-ColorOutput "   • Installation directory: $ReviewGateDir" "White"
Write-ColorOutput "   • MCP server configuration entry" "White"
Write-ColorOutput "   • Global rule file" "White"
Write-ColorOutput "   • Temporary files" "White"
Write-Host ""
Write-ColorOutput "📍 What remains (manual removal needed):" "Cyan"
Write-ColorOutput "   • Cursor extension (remove via Extensions panel)" "White"
Write-ColorOutput "   • SoX installation (if you want to remove it)" "White"
Write-ColorOutput "   • Python virtual environment dependencies" "White"
Write-Host ""
Write-ColorOutput "💡 Configuration backups are preserved for safety" "Yellow"