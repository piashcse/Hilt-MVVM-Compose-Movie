[![3OtOp7R.th.png](https://iili.io/3OtOp7R.th.png)](https://freeimage.host/i/3OtOp7R)
#  Review Gate V2 for Cursor IDE ゲート

**Cursor** would often drop the mic 🎤 way too early! I'd give it a complex task, it'd use maybe 5 of its ~25 available tool calls for that single "main request," then call it a day. Not only was that untapped AI power for that *single thought*, but making small follow-up tweaks meant starting a *new request*. Doing that too often, and my precious **~500 monthly requests** (you know the ones!) would burn up much faster than I liked :( 

**Presenting: The Review Gate V2 – The "Turn Your 500 Cursor Requests into 2500!" Rule with Voice & Vision!**
(Okay, maybe not *always* a perfect 5x, but you get the damn idea! 😉)

I evolved this Global Rule for our beloved Cursor IDE to transform my (and your!) AI from a quick sprinter into an endurance marathon runner for complex ideas, all within the lifecycle of a *single main request*. But now it's **supercharged with voice commands, image uploads, and a beautiful popup interface!** I've basically told Cursor: "Hold up, *we're* not done with this request until *I* say we're done." Before it dares to end the conversation, it *must* open a special **interactive popup** for my (and your!) final, iterative commands with full multi-modal support.

If each main request can now handle the depth of what might have taken 5 separate (and shallow) requests before, we're effectively **supercharging those ~500 monthly requests to feel like 2500 in terms of iterative power!** It's about making every single one count, HARD.

## 🎬 Quick Demo:
**See Review Gate V2 in action!** → https://www.youtube.com/watch?v=mZmNM-AIf4M

## ✨ Key Awesomeness (What V2 Packs In)

* **🎤 Voice-Activated AI Control:** Speak your sub-prompts directly! Click the mic, speak naturally, and watch your words transcribe automatically using local Faster-Whisper AI.
* **📷 Visual Context Sharing:** Upload images, screenshots, diagrams, or mockups directly in the popup. The AI sees everything you share.
* **🎨 Beautiful Popup Interface:** Professional orange-glow design that fits perfectly in Cursor with real-time MCP status indicators.
* **AI On MY Leash:** Makes the Cursor Agent wait for *my* (and your!) "go-ahead" via an interactive popup before it truly signs off on an *initial* request.
* **Multiply Your Request Power:** Make *one* main request do the work of many! Instead of 5 new prompts (and 5 dings on your ~500 request counter!), use the Review Gate for 5 (or more!) iterative sub-prompts *within that single request's lifecycle and tool call budget*.
* **Unlock Full Tool Call Potential:** I designed this to help us guide the AI to use more of its ~25 available tool calls for a *single complex idea* through those sub-prompts.
* **MCP Integration Magic:** Built on the Model Context Protocol for seamless Cursor integration. The popup automatically appears when needed.
* **Cross-Platform Speech:** Whisper speech-to-text works flawlessly on macOS and is implemented for Windows, though Windows support hasn't been extensively tested (take it with a grain of salt!).

## 🛠️ The Guts (How V2 Works, Even Better)

1.  **You (or I):** Give Cursor a task (this counts as 1 main request towards your ~500).
2.  **Cursor AI:** Does its main job (coding, analysis, maybe a few tool calls from the ~25 for this request).
3.  **Review Gate V2 Kicks In (The Magic Part I Evolved!):**
    * AI calls the `review_gate_chat` MCP tool automatically
    * Beautiful popup appears in Cursor with multi-modal input options
    * AI announces it's waiting for your input in the popup
4.  **You (in the popup):** 
    * **Type** quick follow-ups (e.g., "Now add docstrings to all new functions.")
    * **Speak** your commands using the microphone (automatic transcription)
    * **Upload images** for visual context (screenshots, mockups, diagrams)
    * **Or type** `TASK_COMPLETE` when you're satisfied
5.  **Cursor AI (powered by MCP integration):**
    Reads your popup input (text, speech, images), acts on it (more coding, *more tool calls from the original budget*!), responds in the main chat, then opens the popup again for your *next* input.
6.  **Loop!** This continues, deepening the work on your original request, until you type `TASK_COMPLETE` in the popup.

## 🚀 Get It Going (V2 Installation)

**Two simple steps to supercharge your Cursor workflow:**

### Step 1: One-Click Technical Setup

#### macOS Installation (Fully Tested ✅)
```bash
# Clone repository and navigate to V2 subdirectory
git clone https://github.com/LakshmanTurlapati/Review-Gate.git
cd Review-Gate/V2

# Run the magical one-click installer
./install.sh
```

#### Windows Installation (Not extensively tested, might need manual tweaks, try it with a grain of salt)
**PowerShell (Recommended):**
```powershell
# Clone repository and navigate to V2 subdirectory
git clone https://github.com/LakshmanTurlapati/Review-Gate.git
cd Review-Gate/V2

# Run PowerShell installer (may need admin privileges)
./install.ps1
```

**Command Prompt (Alternative):**
```cmd
# Clone repository and navigate to V2 subdirectory
git clone https://github.com/LakshmanTurlapati/Review-Gate.git
cd Review-Gate/V2

# Run batch installer
install.bat
```

The installer automatically handles:
- ✅ **Dependencies**: Package managers (Homebrew/Chocolatey), SoX (for speech), Python packages
- ✅ **MCP Server**: Global installation in `~/cursor-extensions/review-gate-v2/` (macOS) or `%USERPROFILE%\cursor-extensions\review-gate-v2\` (Windows)
- ✅ **Extension**: Cursor extension for the popup interface
- ✅ **Configuration**: MCP integration setup with safe merging of existing configurations

### Step 2: Copy the V2 Rule to Cursor

**CRITICAL STEP**: For the Review Gate V2 to work, you need to copy the V2 rule to your Cursor settings:

1. **Open the Rule File**: Copy the entire contents of `ReviewGateV2.mdc` from this folder
2. **Cursor Settings**: Open your Cursor IDE → Settings (Cmd/Ctrl + ,)
3. **Find Rules Section**: Look for "Rules" or "AI Rules" in the settings
4. **Paste & Save**: Paste the entire V2 rule content and save
5. **Restart Cursor**: Restart Cursor completely for the rule to take effect

**Why this step?** The rule tells Cursor when and how to activate the Review Gate V2 popup. Without it, you'll have a working MCP server but no automatic activation!

## 🧪 Testing Your V2 Installation

After both steps are complete:

1. **Manual Popup Test**: Press `Cmd+Shift+R` in Cursor to open the popup manually
2. **Agent Integration Test**: Ask Cursor: *"Use the review_gate_chat tool to get my feedback"*
3. **Speech Test**: Click the microphone → speak clearly → verify automatic transcription
4. **Image Test**: Click the camera icon → upload an image → send with text
5. **Full Workflow Test**: Give Cursor a complex task and watch the Review Gate V2 popup appear automatically

## 💡 Play Smart (My V2 Tips & The "Why")

* **Why I evolved this hack:** To stop Cursor from ending too soon when I have iterative follow-ups for the *same original thought process*, but now with the power of voice commands and visual context sharing.
* **Voice Commands Work Best When:** You speak clearly and concisely. The local Whisper AI is quite good but prefers natural, well-paced speech.
* **Image Context is Gold:** Upload screenshots of errors, mockups of what you want built, or diagrams of architecture. The AI can see and understand visual context.
* **Platform Notes:** Speech-to-text is rock-solid on macOS. Windows implementation exists but hasn't been battle-tested extensively - your mileage may vary!
* **Be Clear in All Inputs:** Whether typing, speaking, or sharing images, clear and direct communication in the popup works best.
* **`TASK_COMPLETE` is Your Exit:** Don't forget to type this in the popup to let the AI finally rest (and free up that main request slot).

## ⚠️ Heads Up! (My Friendly V2 Warnings)

* **EXPERIMENTAL EVOLUTION!** This is V2 of my power-user move. It works because we're very cleverly instructing the AI with MCP integration.
* **MCP SERVER RUNS LOCALLY:** The rule uses a local MCP server that integrates with Cursor. The installer sets this up automatically.
* **SPEECH PROCESSING IS LOCAL:** Your voice is processed locally using Faster-Whisper AI - nothing goes to the cloud.
* **PLATFORM COMPATIBILITY:** 
  - **macOS**: Fully tested and works flawlessly ✅
  - **Windows**: Implemented with PowerShell and Batch installers (Not extensively tested, might need manual tweaks, try it with a grain of salt) ⚠️
  - **Linux**: Should work but not tested
* **PYTHON & SOX NEEDED:** The installer handles these, but your system needs to support Python 3 and SoX for speech functionality.
* **CURSOR UPDATES MIGHT CHANGE THINGS:** Future Cursor versions could affect how this rule behaves. What works today might need tweaks tomorrow!
* **REMEMBER THE RULE:** The MCP server is just the engine - you MUST copy the V2 rule to your Cursor settings for automatic activation!

## 🎯 What You Get (V2 Feature Summary)

### 🎤 **Voice-to-Text Magic**
- Click microphone → speak naturally → automatic transcription
- Local Faster-Whisper AI processing (no cloud, no privacy concerns)
- Professional visual feedback: mic → red stop button → orange spinner → text injection

### 📷 **Image Upload Power**
- Support for PNG, JPG, JPEG, GIF, BMP, WebP formats
- Drag & drop or click to upload
- Images included in MCP responses so the AI can see your visual context
- Perfect for sharing screenshots, mockups, error dialogs, or architectural diagrams

### 🎨 **Beautiful Interface**
- Clean popup with orange glow design that matches Cursor's aesthetic
- Horizontally aligned controls for professional appearance
- Real-time MCP status indicator
- Smooth animations and responsive feedback

### 🔄 **Seamless MCP Integration**
- Works automatically with Cursor Agent tool calls
- 5-minute timeout for thoughtful responses
- Global installation works across all your Cursor projects
- File-based communication protocol for reliability

## 🔧 V2 Troubleshooting

```bash
# Check if MCP server is running
tail -f /tmp/review_gate_v2.log

# Test speech recording capability
sox --version
sox -d -r 16000 -c 1 test.wav trim 0 3 && rm test.wav

# Check extension logs in Cursor
# Press F12 → Console tab for browser logs

# Verify MCP configuration
cat ~/.cursor/mcp.json

# Test the extension manually
# Press Cmd+Shift+R in Cursor
```

## 🗂️ V2 Files & Structure

```
V2/
├── cursor-extension/           # Cursor extension source
│   ├── extension.js           # Main extension file
│   ├── package.json           # Extension manifest
│   └── review-gate-v2-2.5.2.vsix  # Built extension package
├── review_gate_v2_mcp.py      # MCP server
├── requirements_simple.txt     # Python dependencies
├── ReviewGateV2.mdc           # Global rule file (COPY THIS TO CURSOR!)
├── install.sh                 # One-click installer (macOS)
├── install.ps1               # PowerShell installer (Windows)
├── install.bat               # Batch installer (Windows)
├── uninstall.sh              # Clean uninstaller (macOS)
├── uninstall.ps1             # PowerShell uninstaller (Windows)
├── INSTALLATION.md           # Detailed manual setup guide
├── CLAUDE.md                 # Technical documentation
└── README.md                 # This file
```

## 🎉 Why V2? You Asked For It!

After the original Review Gate gained **100+ forks**, **900+ stars**, **100,000+ impressions**, and countless requests to evolve it further - **you asked for it, I delivered!** The community response was incredible, and the demand for voice commands, visual context, and a more sophisticated interface was overwhelming. V2 is my answer to every single feature request and improvement suggestion.

## 🧑‍💻 About Me & This V2 Evolution

This "Review Gate V2" represents the evolution of my original terminal-based rule into a full-featured, multi-modal interaction system. It was born from my own desire to truly partner with Cursor's AI using not just text, but voice commands and visual context. My goal remains the same: to make every interaction as deep and complete as possible—and ensure every available tool call for a big idea gets its chance to shine, making each of those ~500 requests count like gold!

The V2 system leverages the Model Context Protocol (MCP) to create a seamless bridge between Cursor's AI and a rich, interactive popup interface. Whether you're speaking commands, sharing screenshots, or typing follow-ups, it's all designed to keep you in the flow while maximizing the value of each Cursor request.

To connect with me or learn more about my work, visit: [www.audienclature.com](https://www.audienclature.com)

---

*Happy (and supercharged) coding with Cursor V2! May your AI always await your final command, your voice be clearly transcribed, your images perfectly understood, and your monthly requests feel like they last forever!* ✨🎤📷