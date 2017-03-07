#include <ltsmin/pins.h>
/**
 * @brief calls callback for every successor state of src in transition group "group".
 */
int next_state(void* model, int group, int *src, TransitionCB callback, void *arg);

/**
 * @brief returns the initial state.
 */
int* initial_state();

/**
 * @brief returns the read dependency matrix.
 */
int* read_matrix(int row);

/**
 * @brief returns the write dependency matrix.
 */
int* write_matrix(int row);

/**
 * @brief returns the state label dependency matrix.
 */
int* label_matrix(int row);

/**
 * @brief returns whether the state src satisfies state label "label".
 */
int state_label(void* model, int label, int* src);

/**
 * @brief returns the number of transition groups.
 */
int group_count();

/**
 * @brief returns the length of the state.
 */
int state_length();

/**
 * @brief returns the number of state labels.
 */
int label_count();
