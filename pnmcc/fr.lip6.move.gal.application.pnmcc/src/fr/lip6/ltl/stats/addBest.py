# First, let's read the data to understand its structure and content.
import pandas as pd

# Read the CSV file
file_path = 'formulaStats.csv'
df = pd.read_csv(file_path)

# Display the first few rows to get an overview
#df.head()

# Filter the DataFrame to only include rows with "Type" as "raw", "min", or "max"
df_filtered = df[df['Type'].isin(['raw', 'min', 'max'])]

# Exclude rows with -1 in any column
#df_filtered = df_filtered[(df_filtered != -1).all(axis=1)]

# Step 1: Sort the DataFrame by 'Name' and then by the other criteria
df_sorted = df_filtered.sort_values(
    by=['Name', 'Universal', 'Empty', 'S', 'Edges', 'Acc', 'Formula'],
    ascending=[True, False, False, True, True, True, True]
)

# Initialize state variables
current_name = None
best_row = None
total_time = 0
best_rows_list = []

# Iterate through the sorted DataFrame
for index, row in df_sorted.iterrows():
    # Check if we've moved on to a new 'Name'
    if row['Name'] != current_name:
        # Finalize the "best" row for the previous 'Name'
        if best_row is not None:
            best_row['Time'] = total_time
            best_row['Type'] = 'best'
            best_rows_list.append(best_row)
        
        # Reset state variables for the new 'Name'
        current_name = row['Name']
        best_row = row.copy()
        total_time = 0
    
    # Update the total 'Time' for the current 'Name'
    total_time += row['Time']

# Finalize the last "best" row
if best_row is not None:
    best_row['Time'] = total_time
    best_row['Type'] = 'best'
    best_rows_list.append(best_row)

# Create a DataFrame with the "best" rows
best_rows_df = pd.DataFrame(best_rows_list)

# Concatenate this new DataFrame with the original one
df_with_best = pd.concat([df, best_rows_df], ignore_index=True)

df_with_best = df_with_best.sort_values(
    by=['Name', 'Universal', 'Empty', 'S', 'Edges', 'Acc', 'Formula'],
    ascending=[True, False, False, True, True, True, True]
)

# Display first few rows of the new DataFrame with "best" rows
#df_with_best.head()

df_with_best.to_csv('formulaStatsWBest.csv', index=False)
