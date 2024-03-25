# PowerShell script to set up GitHub repository
# Replace YOUR_USERNAME with your actual GitHub username

$username = Read-Host "Enter your GitHub username"
$repoName = "booking-automation-selenium-java"
$token = Read-Host "Enter your GitHub Personal Access Token" -AsSecureString
$tokenPlain = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($token))

Write-Host "Creating GitHub repository..."

# Create repository using GitHub API
$headers = @{
    "Authorization" = "token $tokenPlain"
    "Accept" = "application/vnd.github.v3+json"
}

$body = @{
    name = $repoName
    description = "Booking.com automation tests using Selenium WebDriver, TestNG, and Java"
    private = $false
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "https://api.github.com/user/repos" -Method Post -Headers $headers -Body $body -ContentType "application/json"
    Write-Host "Repository created successfully: $($response.html_url)" -ForegroundColor Green
    
    # Add remote and push
    Write-Host "Setting up remote and pushing code..."
    git remote remove origin -ErrorAction SilentlyContinue
    git remote add origin "https://$tokenPlain@github.com/$username/$repoName.git"
    git branch -M main
    git push -u origin main
    
    Write-Host "`nRepository setup complete!" -ForegroundColor Green
    Write-Host "Repository URL: $($response.html_url)" -ForegroundColor Cyan
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "You may need to create the repository manually on GitHub.com" -ForegroundColor Yellow
}

