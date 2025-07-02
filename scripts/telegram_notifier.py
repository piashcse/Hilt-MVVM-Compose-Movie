#!/usr/bin/env python3
"""
Telegram Notification Script
Sends work completion notifications to Telegram with screenshot and report
"""

import os
import sys
import json
import requests
import datetime
import subprocess
import platform
import urllib3
from pathlib import Path
from typing import Optional, Dict, Any

# Import configuration from config file
try:
    from scripts.config.telegram_config import TELEGRAM_BOT_TOKEN, TELEGRAM_CHAT_ID, SOCKS5_PROXY
except ImportError:
    # Try relative import for when script is run directly
    try:
        import sys
        from pathlib import Path
        # Add parent directory to path to allow imports
        script_dir = Path(__file__).parent
        sys.path.append(str(script_dir))
        from config.telegram_config import TELEGRAM_BOT_TOKEN, TELEGRAM_CHAT_ID, SOCKS5_PROXY
    except ImportError as e:
        print(f"Error importing telegram config: {e}")
        print("Falling back to environment variables")
        # Fallback to environment variables
        from dotenv import load_dotenv
        load_dotenv()
        TELEGRAM_BOT_TOKEN = os.getenv('TELEGRAM_BOT_TOKEN')
        TELEGRAM_CHAT_ID = os.getenv('TELEGRAM_CHAT_ID')
        SOCKS5_PROXY = None

# Disable SSL warnings for proxy connections
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

# Telegram API URL
TELEGRAM_API_URL = f"https://api.telegram.org/bot{TELEGRAM_BOT_TOKEN}"

# SOCKS5 Proxy Configuration is now imported from config file
# If config import fails, SOCKS5_PROXY will be None and direct connection will be used

class TelegramNotifier:
    def __init__(self, use_proxy: bool = True):
        self.bot_token = TELEGRAM_BOT_TOKEN
        self.chat_id = TELEGRAM_CHAT_ID
        self.api_url = TELEGRAM_API_URL
        self.proxies = SOCKS5_PROXY if use_proxy else None
        
        # Test proxy connection if enabled
        if use_proxy:
            self._test_proxy_connection()
        
    def take_screenshot(self) -> Optional[str]:
        """Take a screenshot and return the file path"""
        timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
        screenshot_path = f"/tmp/screenshot_{timestamp}.png"
        
        try:
            system = platform.system()
            if system == "Darwin":  # macOS
                subprocess.run(["screencapture", "-x", screenshot_path], check=True)
            elif system == "Linux":
                subprocess.run(["gnome-screenshot", "-f", screenshot_path], check=True)
            elif system == "Windows":
                # For Windows, you might need to install additional tools
                subprocess.run(["powershell", "-Command", 
                              f"Add-Type -AssemblyName System.Windows.Forms; [System.Windows.Forms.SendKeys]::SendWait('%{{PRTSC}}'); Start-Sleep -Seconds 1; Get-Clipboard -Format Image | Set-Content -Path '{screenshot_path}' -Encoding Byte"], 
                              check=True)
            else:
                print(f"Unsupported system: {system}")
                return None
                
            if os.path.exists(screenshot_path):
                return screenshot_path
            else:
                print("Screenshot file was not created")
                return None
                
        except subprocess.CalledProcessError as e:
            print(f"Error taking screenshot: {e}")
            return None
        except Exception as e:
            print(f"Unexpected error taking screenshot: {e}")
            return None
    
    def send_message(self, message: str) -> bool:
        """Send a text message to Telegram"""
        url = f"{self.api_url}/sendMessage"
        data = {
            "chat_id": self.chat_id,
            "text": message,
            "parse_mode": "Markdown"
        }
        
        try:
            response = requests.post(url, data=data, proxies=self.proxies, timeout=30)
            response.raise_for_status()
            return True
        except requests.exceptions.RequestException as e:
            print(f"Error sending message: {e}")
            return False
    
    def send_photo_with_caption(self, photo_path: str, caption: str) -> bool:
        """Send a photo with caption to Telegram"""
        url = f"{self.api_url}/sendPhoto"
        
        try:
            with open(photo_path, 'rb') as photo:
                files = {'photo': photo}
                data = {
                    'chat_id': self.chat_id,
                    'caption': caption,
                    'parse_mode': 'Markdown'
                }
                
                response = requests.post(url, files=files, data=data, proxies=self.proxies, timeout=60)
                response.raise_for_status()
                return True
        except FileNotFoundError:
            print(f"Screenshot file not found: {photo_path}")
            return False
        except requests.exceptions.RequestException as e:
            print(f"Error sending photo: {e}")
            return False
    
    def _test_proxy_connection(self) -> bool:
        """Test SOCKS5 proxy connection to Telegram"""
        try:
            test_url = f"{self.api_url}/getMe"
            response = requests.get(test_url, proxies=self.proxies, timeout=10)
            response.raise_for_status()
            print("✅ SOCKS5 proxy connection successful")
            return True
        except Exception as e:
            print(f"⚠️ SOCKS5 proxy connection failed: {e}")
            print("This might be due to missing PySocks library or network issues.")
            print("Falling back to direct connection...")
            self.proxies = None
            return False
    
    def generate_work_report(self, task_name: str, status: str, details: str = "") -> str:
        """Generate a formatted work report"""
        timestamp = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        project_path = os.getcwd()
        project_name = os.path.basename(project_path)
        
        # Get git info if available
        git_info = ""
        try:
            branch = subprocess.check_output(["git", "branch", "--show-current"], 
                                           cwd=project_path, 
                                           stderr=subprocess.DEVNULL).decode().strip()
            commit = subprocess.check_output(["git", "rev-parse", "--short", "HEAD"], 
                                           cwd=project_path, 
                                           stderr=subprocess.DEVNULL).decode().strip()
            git_info = f"\n🌿 *Branch:* `{branch}`\n📝 *Commit:* `{commit}`"
        except:
            pass
        
        status_emoji = {
            "completed": "✅",
            "failed": "❌",
            "in_progress": "⏳",
            "started": "🚀"
        }.get(status.lower(), "📋")
        
        report = f"""🤖 *Work Notification*

{status_emoji} *Status:* {status.upper()}
📋 *Task:* {task_name}
🕐 *Time:* {timestamp}
📁 *Project:* {project_name}{git_info}

📍 *Path:* `{project_path}`"""
        
        if details:
            report += f"\n\n📝 *Details:*\n{details}"
        
        return report
    
    def notify_work_completion(self, task_name: str, status: str = "completed", 
                             details: str = "", include_screenshot: bool = True) -> bool:
        """Send work completion notification with optional screenshot"""
        report = self.generate_work_report(task_name, status, details)
        
        if include_screenshot:
            screenshot_path = self.take_screenshot()
            if screenshot_path:
                success = self.send_photo_with_caption(screenshot_path, report)
                # Clean up screenshot file
                try:
                    os.remove(screenshot_path)
                except:
                    pass
                return success
            else:
                # Fallback to text message if screenshot fails
                report += "\n\n⚠️ *Note:* Screenshot capture failed"
                return self.send_message(report)
        else:
            return self.send_message(report)

def main():
    """Main function for command line usage"""
    if len(sys.argv) < 3:
        print("Usage: python telegram_notifier.py <task_name> <status> [details] [--no-screenshot]")
        print("Status options: completed, failed, in_progress, started")
        print("Example: python telegram_notifier.py 'Setup Database' completed 'All tables created successfully'")
        sys.exit(1)
    
    task_name = sys.argv[1]
    status = sys.argv[2]
    details = sys.argv[3] if len(sys.argv) > 3 and not sys.argv[3].startswith('--') else ""
    include_screenshot = "--no-screenshot" not in sys.argv
    
    notifier = TelegramNotifier()
    success = notifier.notify_work_completion(task_name, status, details, include_screenshot)
    
    if success:
        print("✅ Notification sent successfully!")
    else:
        print("❌ Failed to send notification")
        sys.exit(1)

if __name__ == "__main__":
    main()