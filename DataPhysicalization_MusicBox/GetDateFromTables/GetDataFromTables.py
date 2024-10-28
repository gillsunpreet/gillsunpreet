import pandas as pd
import matplotlib.pyplot as plt


# loading the csv file
file_path1 = r'C:\Users\preet\Downloads\A_CSC413_Ass2\Top100_1980s.csv'
file_path2 = r'C:\Users\preet\Downloads\A_CSC413_Ass2\Top100_1990s.csv'
file_path3 = r'C:\Users\preet\Downloads\A_CSC413_Ass2\Top100_2000s.csv'
file_path4 = r'C:\Users\preet\Downloads\A_CSC413_Ass2\Top100_2010s.csv'
#If you just change the variable number to 1, 2, 3 or 4 in 'file_path4' variable below then it will load data for different decades 
df = pd.read_csv(file_path1)

# Define a function to categorize genres into the 4 main categories
def categorize_genre(genre):
    # Categories to check for each genre
    pop_keywords = ['Pop', 'Slow', 'Alternative', 'TBD', 'CHR', 'Easy', 'Indie Pop', 'Latin Pop', 'Funk', 'Soul']
    rock_keywords = ['Rock', 'Fast Rock', 'Indie Folk', 'Pop Rock']
    rnb_keywords = ['R&B', 'Hip-Hop', 'Rap', 'Urban', 'Trap', 'Country Rap']
    dance_keywords = ['Dance', 'EDM', 'Disco', 'Dance-Pop', 'Electro House', 'Electronic', 'Dancehall']
    
    if pd.isna(genre):  # Handle NaN values
        return 'Other'
    
    genre = genre.lower()  # Convert to lowercase for consistent matching
    
    # Check for each category and assign
    if any(keyword.lower() in genre for keyword in pop_keywords):
        return 'Pop'
    elif any(keyword.lower() in genre for keyword in rock_keywords):
        return 'Rock'
    elif any(keyword.lower() in genre for keyword in rnb_keywords):
        return 'Hip-Hop/Rap/R&B'
    elif any(keyword.lower() in genre for keyword in dance_keywords):
        return 'Dance/EDM'
    else:
        return 'Other'  # If it doesn't fit in any of the 4 categories

# Apply the categorization function to the 'Genre' column
df['Main_Genre'] = df['Genre'].apply(categorize_genre)

# Count the number of songs in each main genre
genre_counts = df['Main_Genre'].value_counts()

# Display the counts for each category
print("Number of songs in each main genre 1990s:")
print(genre_counts)
# Plotting the bar chart
plt.figure(figsize=(8, 6))
genre_counts.plot(kind='bar', color='skyblue')
plt.title('Number of Songs in Top100 List (2010s)', fontweight = 'bold', fontsize = 16)
plt.xlabel('Genres', fontweight='bold', fontsize = 16, color = 'red')
plt.ylabel('Count of Songs', fontweight='bold', fontsize = 16, color = 'navy', labelpad=20, rotation=90, ha='left')
plt.xticks(rotation=45, fontweight='bold', fontsize = 14)
plt.yticks(range(0, 51, 5), fontweight='bold', fontsize = 12)  # Making the y-axis ticks bold and setting the range to 50
plt.tight_layout()

# Show the plot
plt.show()